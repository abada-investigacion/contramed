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

import com.abada.commons.fingerprint.Reader;
import com.abada.commons.fingerprint.ReaderFacade;
import com.abada.commons.fingerprint.ReaderFactory;
import com.abada.commons.fingerprint.ReaderInformation;
import com.abada.commons.fingerprint.ReadersFactory;
import com.abada.commons.fingerprint.enums.FingerIndex;
import com.abada.commons.fingerprint.enums.ReaderType;
import com.abada.figerprintdb4o.Db4oUserDatabase;
import com.abada.figerprintdb4o.data.User;
import com.abada.figerprintdb4o.UserDatabase;
import java.util.List;
import java.util.Scanner;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author katsu
 */
public class Main implements ReaderFacade {

    public static void main(String[] args) {
        System.out.println("Opciones read or create");

        UserDatabase db = new Db4oUserDatabase();
        ReaderFactory f = ReadersFactory.getInstance().getFactory(ReaderType.DIGITAL_PERSONA);

        List<ReaderInformation> readers = f.getReaders();
        Reader reader = f.getReader(readers.get(0));

        if (args.length == 1) {            
            if (args[0].equals("read")) {
                System.out.println("Coloca el dedo gordo derecho en el lector.");
                User user = db.getUserByFingerPrint(reader.readForVerification(), f.getVerificator());
                System.out.println(user.getUsername());
            } else if (args[0].equals("create")) {
                System.out.println("Coloca el dedo gordo derecho en el lector y siga las instrucciones.");
                byte[] finger = reader.readForSave(FingerIndex.RIGHT_THUMB, new Main());

                System.out.println("Inserta nombre del usuario:");
                Scanner in = new Scanner(System.in);
                String username = in.nextLine();

                User user = new User();
                user.setUsername(username);
                user.getTemplates().put(FingerIndex.RIGHT_THUMB, finger);

                db.addUser(user);
            }
        }
    }

    public void onNextRead(FingerIndex fi) {
        System.out.println("Ponga el dedo sobre el lector.");
    }
}
