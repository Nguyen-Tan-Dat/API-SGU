package net.sgu.api.models;

import net.sgu.api.models.dto.BuoiHoc;

import java.util.ArrayList;
import java.util.List;

public class HocPhan {
    private String MaMH;
    private String NhomMH;
    private String ToTH;
    private String TenMH;
    private byte SoTinChi;
    private byte STCHP;
    private String MaLop;
    private int SiSo;
    private int ConLai;
    private String ThucHanh;
    private List<Byte> Thu;
    private List<Byte> TietBD;
    private List<Byte> SoTiet;
    private String Phong;
    private String GiangVien;
    private String TuanDiHoc;

    private List<BuoiHoc> dsBuiHoc;

    public List<BuoiHoc> getDsBuiHoc() {
        return dsBuiHoc;
    }

    public HocPhan(String maMH, String tenMH, String nhomMH, String toTH, byte soTinChi, byte STCHP, String maLop, int siSo, int conLai, String thucHanh, String thu, String tietBD, String soTiet, String phong, String giangVien, String tuanDiHoc) {
        MaMH = maMH;
        NhomMH = nhomMH;
        ToTH = toTH;
        TenMH = tenMH;
        SoTinChi = soTinChi;
        this.STCHP = STCHP;
        MaLop = maLop;
        SiSo = siSo;
        ConLai = conLai;
        ThucHanh = thucHanh;
        stringThu = thu;
        stringTBD = tietBD;
        stringST = soTiet;
        Thu = convertDays(thu);
        TietBD = convertNumbers(tietBD);
        SoTiet = convertNumbers(soTiet);
        Phong = phong;
        GiangVien = giangVien;
        TuanDiHoc = tuanDiHoc;
    }

    public static HocPhan toHocPhan(String[] data) {
        try {
            int conLai = 0;
            try {
                conLai = Integer.parseInt(data[9]);
            } catch (NumberFormatException ignored) {
            }
            return new HocPhan(data[1], data[2], data[3], data[4], Byte.parseByte(data[5]), Byte.parseByte(data[6]), data[7], Integer.parseInt(data[8]), conLai, data[10], data[11], data[12], data[13], data[14], data[15], data[16]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getMaMH() {
        return MaMH;
    }

    public String getNhomMH() {
        return NhomMH;
    }

    public String getToTH() {
        return ToTH;
    }

    public String getTenMH() {
        return TenMH;
    }

    public byte getSoTinChi() {
        return SoTinChi;
    }

    public byte getSTCHP() {
        return STCHP;
    }

    public String getMaLop() {
        return MaLop;
    }

    public void setMaLop(String maLop) {
        MaLop = maLop;
    }

    public int getSiSo() {
        return SiSo;
    }

    public int getConLai() {
        return ConLai;
    }

    public String getThucHanh() {
        return ThucHanh;
    }

    public String getPhong() {
        return Phong;
    }

    public String getGiangVien() {
        return GiangVien;
    }

    public String getTuanDiHoc() {
        return TuanDiHoc;
    }

    public List<Byte> getDays() {
        return Thu;
    }

    public List<Byte> getLessonStart() {
        return this.TietBD;
    }

    public List<Byte> getLessonAmount() {
        return this.SoTiet;
    }

    public static byte dayToByte(String day) {
        String[] days = {"Hai", "Ba", "Tư", "Năm", "Sáu", "Bảy"};
        for (byte i = 0; i < days.length; i++)
            if (days[i].equals(day)) return i;
        return 6;
    }

    public static String byteToDay(byte day) {
        String[] days = {"Hai", "Ba", "Tư", "Năm", "Sáu", "Bảy", "Chủ Nhật"};
        try {
            return days[day];
        } catch (Exception e) {
            return days[days.length-1];
        }
    }

    private List<Byte> convertDays(String days) {
        String[] list = days.split(" ");
        List<Byte> rs = new ArrayList<>();
        for (String s : list) {
            rs.add(dayToByte(s));
        }
        return rs;
    }

    private List<Byte> convertNumbers(String numbers) {
        String[] list = numbers.split(" ");
        List<Byte> rs = new ArrayList<>();
        for (String s : list) {
            byte num = 0;
            try {
                num = Byte.parseByte(s);
            } catch (Exception ignored) {
            }
            rs.add(num);
        }
        return rs;
    }

    private String stringThu;
    private String stringTBD;

    public String getThu() {
        return stringThu;
    }

    public String getTietBD() {
        return stringTBD;
    }

    private String stringST;

    public String getSoTiet() {

        return stringST;
    }

    @Override
    public String toString() {
        return "HocPhan{" +
                "MaMH='" + MaMH + '\'' +
                ", NhomMH='" + NhomMH + '\'' +
                ", ToTH='" + ToTH + '\'' +
                ", TenMH='" + TenMH + '\'' +
                ", SoTinChi=" + SoTinChi +
                ", STCHP=" + STCHP +
                ", MaLop='" + MaLop + '\'' +
                ", SiSo=" + SiSo +
                ", ConLai=" + ConLai +
                ", ThucHanh='" + ThucHanh + '\'' +
                ", Thu=" + Thu +
                ", TietBD=" + TietBD +
                ", SoTiet=" + SoTiet +
                ", Phong='" + Phong + '\'' +
                ", GiangVien='" + GiangVien + '\'' +
                ", TuanDiHoc='" + TuanDiHoc + '\'' +
                ", dsBuiHoc=" + dsBuiHoc +
                ", stringThu='" + stringThu + '\'' +
                ", stringTBD='" + stringTBD + '\'' +
                ", stringST='" + stringST + '\'' +
                '}';
    }
}
