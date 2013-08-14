/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abada.farmatools.main;

import com.abada.farmatools.enums.Adt;
import com.abada.farmatools.enums.OrderControl;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author tecnicocontramed
 */
public class StatisticsModule {

    private Map<Adt, Integer> ADTInvalidStatistics;
    private Map<Adt, Integer> ADTOKStatistics;
    private Map<Adt, Integer> ADTKOStatistics;

    private Map<OrderControl, Integer> OMPInvalidStatistics;
    private Map<OrderControl, Integer> OMPOKStatistics;
    private Map<OrderControl, Integer> OMPKOStatistics;

    private Calendar startTime;
    private Calendar endTime;

    private SimpleDateFormat format;

    public StatisticsModule() {

        ADTInvalidStatistics = new EnumMap<Adt, Integer>(Adt.class);
        ADTOKStatistics = new EnumMap<Adt, Integer>(Adt.class);
        ADTKOStatistics = new EnumMap<Adt, Integer>(Adt.class);
        OMPInvalidStatistics = new EnumMap<OrderControl, Integer>(OrderControl.class);
        OMPOKStatistics = new EnumMap<OrderControl, Integer>(OrderControl.class);
        OMPKOStatistics = new EnumMap<OrderControl, Integer>(OrderControl.class);

        for (Adt adt : Adt.values()) {
            ADTOKStatistics.put(adt, new Integer(0));
            ADTKOStatistics.put(adt, new Integer(0));
            ADTInvalidStatistics.put(adt, new Integer(0));
        }
        for (OrderControl order : OrderControl.values()) {
            OMPOKStatistics.put(order, new Integer(0));
            OMPKOStatistics.put(order, new Integer(0));
            OMPInvalidStatistics.put(order, new Integer(0));
        }

        format = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
    }

    public void pickEndTime() {
        this.endTime = Calendar.getInstance();
    }

    public void pickStartTime() {
        this.startTime = Calendar.getInstance();
    }

    public void incInvalidADT(Adt adt){
        ADTInvalidStatistics.put(adt, new Integer(ADTInvalidStatistics.get(adt).intValue())+1);
    }

    public void incOKADT(Adt adt){
        ADTOKStatistics.put(adt, new Integer(ADTOKStatistics.get(adt).intValue())+1);
    }

    public void incKOADT(Adt adt){
        ADTKOStatistics.put(adt, new Integer(ADTKOStatistics.get(adt).intValue())+1);
    }

    public void incInvalidOMP(OrderControl order){
        OMPInvalidStatistics.put(order, new Integer(OMPInvalidStatistics.get(order).intValue())+1);
    }

    public void incOKOMP(OrderControl order){
        OMPOKStatistics.put(order, new Integer(OMPOKStatistics.get(order).intValue())+1);
    }

    public void incKOOMP(OrderControl order){
        OMPKOStatistics.put(order, new Integer(OMPKOStatistics.get(order).intValue())+1);
    }

    private int totalInvalidADTs(){
       int total = 0;
       for(Integer i:ADTInvalidStatistics.values()){
           total += i.intValue();
       }
       return total;
    }

    private int totalOKADTs(){
       int total = 0;
       for(Integer i:ADTOKStatistics.values()){
           total += i.intValue();
       }
       return total;
    }

    private int totalKOADTs(){
       int total = 0;
       for(Integer i:ADTKOStatistics.values()){
           total += i.intValue();
       }
       return total;
    }

    private int totalInvalidOMPs(){
       int total = 0;
       for(Integer i:OMPInvalidStatistics.values()){
           total += i.intValue();
       }
       return total;
    }

    private int totalOKOMPs(){
       int total = 0;
       for(Integer i:OMPOKStatistics.values()){
           total += i.intValue();
       }
       return total;
    }

    private int totalKOOMPs(){
       int total = 0;
       for(Integer i:OMPKOStatistics.values()){
           total += i.intValue();
       }
       return total;
    }

    private String totalValidADT(Adt adt){
        return adt.toString()+"="+(ADTOKStatistics.get(adt).intValue()+ADTKOStatistics.get(adt).intValue());
    }

    private String  totalValidOMP(OrderControl order){
        return order.toString()+"="+(OMPOKStatistics.get(order).intValue()+OMPKOStatistics.get(order).intValue());
    }

    @Override
    public String toString(){
        StringBuilder stats = new StringBuilder();
        stats.append("\n\n==========STATISTICS==========n\n");

        stats.append("Start time : ");
        stats.append(format.format(startTime.getTime()));
        stats.append("\n");
        stats.append("End time : ");
        stats.append(format.format(endTime.getTime()));
        stats.append("\n");

        long milliseconds=endTime.getTimeInMillis()-startTime.getTimeInMillis();
        long hours = milliseconds/(1000*60*60);
        milliseconds = (milliseconds%(1000*60*60));
        long minutes = milliseconds/(1000*60);
        milliseconds = (milliseconds%(1000*60));
        long seconds = milliseconds/1000;

        stats.append("Total duration: ");
        stats.append("Hours ");
        stats.append(hours);
        stats.append(" Minutes ");
        stats.append(minutes);
        stats.append(" Seconds ");
        stats.append(seconds);
        stats.append("\n\n");

        /*stats.append("Total duration: ");
        stats.append("Hours ");
        stats.append(endTime.get(Calendar.HOUR_OF_DAY)-startTime.get(Calendar.HOUR_OF_DAY));
        stats.append(" Minutes ");
        stats.append(endTime.get(Calendar.MINUTE)-startTime.get(Calendar.MINUTE));
        stats.append(" Seconds ");
        stats.append(endTime.get(Calendar.SECOND)-startTime.get(Calendar.SECOND));
        stats.append("\n\n");*/

        int totalInvalidADTs = totalInvalidADTs();
        int totalOKADTs = totalOKADTs();
        int totalKOADTs = totalKOADTs();
        int totalInvalidOMPs = totalInvalidOMPs();
        int totalOKOMPs = totalOKOMPs();
        int totalKOOMPs = totalKOOMPs();

        //Totals
        stats.append("*GLOBAL TOTALS :\n");
        stats.append("Total Messages: ");
        stats.append((totalInvalidADTs+totalOKADTs+totalKOADTs+totalInvalidOMPs+totalOKOMPs+totalKOOMPs));
        stats.append("\n");
        stats.append("Valid messages :");
        stats.append((totalOKADTs+totalKOADTs+totalOKOMPs+totalKOOMPs));
        stats.append("\n");
        stats.append("\\---OK messages : ");
        stats.append((totalOKADTs+totalOKOMPs));
        stats.append("\n");
        stats.append("\\---KO messages : ");
        stats.append((totalKOADTs+totalKOOMPs));
        stats.append("\n");
        stats.append("Invalid messages : ");
        stats.append((totalInvalidADTs+totalInvalidOMPs));
        stats.append("\n\n");

        //Totals ADTs
        stats.append("*Total ADT Messages: ");
        stats.append((totalInvalidADTs+totalOKADTs+totalKOADTs));
        stats.append("\n");
        stats.append("Valid ADT messages :");
        stats.append((totalOKADTs+totalKOADTs));
        stats.append("\n");
        stats.append("\\---OK ADT messages : ");
        stats.append((totalOKADTs));
        stats.append("\n");
        stats.append("\\---KO ADT messages : ");
        stats.append((totalKOADTs));
        stats.append("\n");
        stats.append("Invalid ADT messages : ");
        stats.append((totalInvalidADTs));
        stats.append("\n\n");

        //Totals OMP
        stats.append("*Total OMPO09 Messages :");
        stats.append((totalInvalidOMPs+totalOKOMPs+totalKOOMPs));
        stats.append("\n");
        stats.append("Valid OMPO09 messages :");
        stats.append((totalOKOMPs+totalKOOMPs));
        stats.append("\n");
        stats.append("\\---OK OMPO09 messages : ");
        stats.append((totalOKOMPs));
        stats.append("\n");
        stats.append("\\---KO OMPO09 messages : ");
        stats.append((totalKOOMPs));
        stats.append("\n");
        stats.append("Invalid OMPO09 messages : ");
        stats.append((totalInvalidOMPs));
        stats.append("\n\n");

        //Details ADT Messages
        stats.append("*Details ADT Messages:\n");
        stats.append("Valids:\n");
        for(Adt adt:Adt.values()){
            stats.append(totalValidADT(adt));
            stats.append("\n");
        }
        stats.append("\n");
        stats.append("OK:\n");
        for(Entry<Adt,Integer> entry:ADTOKStatistics.entrySet()){
            stats.append(entry.toString());
            stats.append("\n");
        }
        stats.append("\n");
        stats.append("KO:\n");
        for(Entry<Adt,Integer> entry:ADTKOStatistics.entrySet()){
            stats.append(entry.toString());
            stats.append("\n");
        }
        stats.append("\n");
        stats.append("Invalids:\n");
        for(Entry<Adt,Integer> entry:ADTInvalidStatistics.entrySet()){
            stats.append(entry.toString());
            stats.append("\n");
        }
        stats.append("\n\n");

        //Details OMPO09 Messages
        stats.append("*Details OMPO09 Messages:\n");
        stats.append("Valids:\n");
        for(OrderControl order:OrderControl.values()){
            stats.append(totalValidOMP(order));
            stats.append("\n");
        }
        stats.append("\n");
        stats.append("OK:\n");
        for(Entry<OrderControl,Integer> entry:OMPOKStatistics.entrySet()){
            stats.append(entry.toString());
            stats.append("\n");
        }
        stats.append("\n");
        stats.append("KO:\n");
        for(Entry<OrderControl,Integer> entry:OMPKOStatistics.entrySet()){
            stats.append(entry.toString());
            stats.append("\n");
        }
        stats.append("\n");
        stats.append("Invalids:\n");
        for(Entry<OrderControl,Integer> entry:OMPInvalidStatistics.entrySet()){
            stats.append(entry.toString());
            stats.append("\n");
        }
        stats.append("\n\n");
        return stats.toString();
    }
}
