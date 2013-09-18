/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.webcontramed.controller;

/*
 * #%L
 * Contramed
 * %%
 * Copyright (C) 2013 Abada Servicios Desarrollo (investigacion@abadasoft.com)
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
import com.abada.commons.fingerprint.ReaderFactory;
import com.abada.commons.fingerprint.ReadersFactory;
import com.abada.commons.fingerprint.enums.ReaderType;
import com.abada.contramed.persistence.dao.login.LoginDao;
import com.abada.contramed.persistence.dao.staff.StaffDao;
import com.abada.contramed.persistence.entity.Staff;
import com.abada.figerprintdb4o.Db4oUserDatabase;
import com.abada.figerprintdb4o.UserDatabase;
import com.abada.figerprintdb4o.data.User;
import com.abada.springframework.web.servlet.view.JsonView;
import com.abada.springframework.web.servlet.view.StringView;
import com.abada.web.exjs.Success;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author katsu
 */
@Controller
public class LoginController extends CommonControllerConstants {

    private LoginDao loginDao;

    @Resource(name = "loginDao")
    public void setLoginDao(LoginDao loginDao) {
        this.loginDao = loginDao;
    }
    private StaffDao staffDao;

    @Resource(name = "StaffDao")
    public void setStaffDao(StaffDao staffDao) {
        this.staffDao = staffDao;
    }

    @RequestMapping("/login.htm")
    public ModelAndView login(HttpServletRequest request) {
        //Elimino la posible session anterior
        this.invalidateSession(request);
        Map<String, String> model = new HashMap<String, String>();
        model.put("js", "login.js");
        return new ModelAndView("pages/initial", model);
    }

    @RequestMapping("/decode.htm")
    public ModelAndView login(@RequestBody String imageBase64, HttpServletRequest request) {
        String resultString=null;
        try {
            imageBase64= imageBase64.substring(imageBase64.indexOf(',')+1);            
            byte[] bs = DatatypeConverter.parseBase64Binary(imageBase64);

            Reader reader = new MultiFormatReader();

            InputStream in = new ByteArrayInputStream(bs);
            BufferedImage image = ImageIO.read(in);

            int w=image.getWidth(),h=image.getHeight();
            int [] data=image.getRGB(0, 0, w, h, null, 0, w);
            
            RGBLuminanceSource source=new RGBLuminanceSource(w,h,data);
            HybridBinarizer hybridBinarizer=new HybridBinarizer(source);
            BinaryBitmap binaryBitmap=new BinaryBitmap(hybridBinarizer);
            
            Result result=reader.decode(binaryBitmap);           
            resultString=result.getText();
            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ModelAndView(new StringView(resultString,null));
    }

    /**
     * Valida el login y password y guarda en sesion el Staff y el Role del
     * usuario
     *
     * @param login
     * @param password
     * @param request
     * @return
     */
    @RequestMapping("/loginvalidation.htm")
    public ModelAndView loginValidation(String login, String password, HttpServletRequest request) {
        //Elimino la posible session anterior
        this.invalidateSession(request);
        Success result = new Success(false);
        Staff staff = loginDao.login(login, password);
        if (staff != null && !staff.isHistoric()) {
            this.createSession(request);
            this.setStaff(request, staff);
            this.setRole(request, staff.getRole().toString());
            result.setSuccess(Boolean.TRUE);
        }
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/getUserName.htm")
    public ModelAndView getUserName(String tag, HttpServletRequest request) {
        /*ExtjsStore result = new ExtjsStore();
         result.setData(this.loginDao.getUsernameByTag(tag));
         return new ModelAndView(new JsonView(result));*/

        //Elimino la posible session anterior
        this.invalidateSession(request);
        Success result = new Success(false);
        List<Staff> staffs = this.staffDao.findByTag(tag);
        if (staffs != null && !staffs.isEmpty()) {
            if (!staffs.get(0).isHistoric()) {
                this.createSession(request);
                this.setStaff(request, staffs.get(0));
                this.setRole(request, staffs.get(0).getRole().toString());
                result.setSuccess(Boolean.TRUE);
            }
        }
        return new ModelAndView(new JsonView(result));
    }

    @RequestMapping("/getUserNameByFinger.htm")
    public ModelAndView getUserNameByFinger(String tag, HttpServletRequest request) {
        /*ExtjsStore result = new ExtjsStore();
         result.setData(this.loginDao.getUsernameByTag(tag));
         return new ModelAndView(new JsonView(result));*/

        //Elimino la posible session anterior
        this.invalidateSession(request);

        Success result = new Success(false);

        UserDatabase db = new Db4oUserDatabase();
        ReaderFactory f = ReadersFactory.getInstance().getFactory(ReaderType.DIGITAL_PERSONA);
        try {
            //FIXME Only work if Server is in Windows and with drivers for UareU 4500 installed
            /*User user = db.getUserByFingerPrint(new BASE64Decoder().decodeBuffer(tag), f.getVerificator());
             if (user!=null){*/
            //FIXME Comment 3 next lines if you want fingerprints
            List<User> users = db.getAllUser();
            User user = users.get(0);
            if (users.size() > 0) {
                List<Staff> staffs = this.staffDao.findByUsername(user.getUsername());
                if (staffs != null && !staffs.isEmpty()) {
                    if (!staffs.get(0).isHistoric()) {
                        this.createSession(request);
                        this.setStaff(request, staffs.get(0));
                        this.setRole(request, staffs.get(0).getRole().toString());
                        result.setSuccess(Boolean.TRUE);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView(new JsonView(result));
    }
}
