package net.sgu.api.models;

import java.util.ArrayList;
import java.util.List;

public class ThoiKhoaBieu {
    private  List<HocPhan> dsHocPhan;

    public ThoiKhoaBieu(List<HocPhan> dsHocPhan) {
        this.dsHocPhan = new ArrayList<>();
        this.dsHocPhan.addAll(dsHocPhan);
    }

    public List<HocPhan> getDsHocPhan() {
        return dsHocPhan;
    }
    public static boolean isTKB(List<HocPhanDTO> dsHP){
        boolean[][] table = new boolean[16][7];
        for (byte i = 1; i < 10; i++)
            for (byte j = 0; j < 7; j++)
                table[i][j] = false;
        for (HocPhanDTO mh : dsHP) {
            for (byte i = 0; i < mh.getTimes().size(); i++) {
                byte lessonStart = mh.getTimes().get(i)[1];
                byte lessonAmount = mh.getTimes().get(i)[2];
                byte day = mh.getTimes().get(i)[0];
                for (byte j = lessonStart; j < lessonStart + lessonAmount; j++)
                    if (table[j][day]) return false;
                    else table[j][day] = true;
            }
        }
        return true;
    }

    public boolean hopLe() {
        boolean[][] table = new boolean[11][7];
        for (byte i = 1; i < 11; i++)
            for (byte j = 0; j < 7; j++)
                table[i][j] = false;
        for (HocPhan mh : dsHocPhan) {
            var days = mh.getDays();
            for (byte i = 0; i < days.size(); i++) {
                byte lessonStart = mh.getLessonStart().get(i);
                byte lessonAmount = mh.getLessonAmount().get(i);
                byte day = days.get(i);
                for (byte j = lessonStart; j < lessonStart + lessonAmount; j++)
                    if (table[j][day]) return false;
                    else table[j][day] = true;
            }
        }
        return true;
    }

    public byte soNgayDiHoc() {
        boolean[] dayStudy = {false, false, false, false, false, false,false};
        for (var hp : this.dsHocPhan)
            for (byte day : hp.getDays())
                dayStudy[day] = true;
        byte number = 0;
        for (boolean b : dayStudy)
            if (b) number++;
        return number;
    }
    public static byte soNgayDiHoc(List<HocPhanDTO> dsHP) {
        boolean[] dayStudy = {false, false, false, false, false, false,false};
        for (var hp : dsHP)
            for (byte[] i : hp.getTimes())
                dayStudy[i[0]] = true;
        byte number = 0;
        for (boolean b : dayStudy)
            if (b) number++;
        return number;
    }

    @Override
    public String toString() {
        return "ThoiKhoaBieu{" +
                "dsHocPhan=" + dsHocPhan +
                '}';
    }
}
