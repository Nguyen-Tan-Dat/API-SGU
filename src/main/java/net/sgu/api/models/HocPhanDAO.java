package net.sgu.api.models;

import net.sgu.api.models.dto.HocPhanDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HocPhanDAO {

    public HocPhanDAO() {
    }

    public static List<List<HocPhanDTO>> dsHPbyMH(Set<String> dsMaMH) {
        List<List<HocPhanDTO>> dsHPbyMH = new ArrayList<>();
        for (var i : dsMaMH) {
            List<HocPhanDTO> dsHP = new ArrayList<>();
            try {
                PreparedStatement statement = Database.getConnect().prepareStatement("SELECT n.ma,ma_mh,ten,so_tin_chi,so,to_th,si_so,con_lai FROM mon_hoc mh,nhom n WHERE n.ma_mh=mh.ma and mh.ma='" + i + "';");
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    dsHP.add(new HocPhanDTO(
                            rs.getString("ma_mh"),
                            rs.getString("ten"),
                            rs.getByte("so_tin_chi"),
                            rs.getString("so"),
                            rs.getString("to_th"),
                            rs.getInt("si_so"),
                            rs.getInt("con_lai"),
                            BuoiHocDAO.getByNhom(rs.getInt(1))
                    ));
                }
                rs.close();
                statement.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
            dsHPbyMH.add(dsHP);
        }
        return dsHPbyMH;
    }

    public static List<List<HocPhanDTO>> dsHPbyMH(Set<String> dsMaMH, Set<Byte> thu) {
        List<List<HocPhanDTO>> dsHPbyMH = new ArrayList<>();
        for (var i : dsMaMH) {
            List<HocPhanDTO> dsHP = new ArrayList<>();
            try {
                PreparedStatement statement = Database.getConnect().prepareStatement("SELECT n.ma,ma_mh,ten,so_tin_chi,so,to_th,si_so,con_lai FROM mon_hoc mh,nhom n WHERE n.ma_mh=mh.ma and mh.ma='" + i + "'");
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    var bhs = BuoiHocDAO.getByNhom(rs.getInt(1));
                    boolean add = true;
                    for (var bh : bhs)
                        if (!thu.contains(HocPhan.dayToByte(bh.thu()))) {
                            add = false;
                            break;
                        }
                    if (add) dsHP.add(new HocPhanDTO(
                            rs.getString("ma_mh"),
                            rs.getString("ten"),
                            rs.getByte("so_tin_chi"),
                            rs.getString("so"),
                            rs.getString("to_th"),
                            rs.getInt("si_so"),
                            rs.getInt("con_lai"),
                            bhs
                    ));
                }
                rs.close();
                statement.close();
            } catch (SQLException ignored) {
            }
            dsHPbyMH.add(dsHP);
        }
        return dsHPbyMH;
    }

    public static List<List<HocPhanDTO>> dsHPbyMHAndGV(Set<String> dsMaMH, Set<String> gv) {
        List<List<HocPhanDTO>> dsHPbyMH = new ArrayList<>();
        for (var i : dsMaMH) {
            List<HocPhanDTO> dsHP = new ArrayList<>();
            try {
                PreparedStatement statement = Database.getConnect().prepareStatement("SELECT n.ma,ma_mh,ten,so_tin_chi,so,to_th,si_so,con_lai FROM mon_hoc mh,nhom n WHERE n.ma_mh=mh.ma and mh.ma='" + i + "'");
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    var bhs = BuoiHocDAO.getByNhomAndGVs(rs.getInt(1), gv);
                    if (bhs.size() > 0) dsHP.add(new HocPhanDTO(
                            rs.getString("ma_mh"),
                            rs.getString("ten"),
                            rs.getByte("so_tin_chi"),
                            rs.getString("so"),
                            rs.getString("to_th"),
                            rs.getInt("si_so"),
                            rs.getInt("con_lai"),
                            bhs
                    ));
                }
                rs.close();
                statement.close();
            } catch (SQLException ignored) {
            }
            dsHPbyMH.add(dsHP);
        }
        return dsHPbyMH;
    }

    public static List<List<HocPhanDTO>> dsHPbyMH(Set<String> dsMaMH, Set<Byte> thu, Set<String> giangVien) {
        List<List<HocPhanDTO>> dsHPbyMH = new ArrayList<>();
        for (var i : dsMaMH) {
            List<HocPhanDTO> dsHP = new ArrayList<>();
            try {
                PreparedStatement statement = Database.getConnect().prepareStatement("SELECT n.ma,ma_mh,ten,so_tin_chi,so,to_th,si_so,con_lai FROM mon_hoc mh,nhom n WHERE n.ma_mh=mh.ma and mh.ma='" + i + "'");
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    var bhs = BuoiHocDAO.getByNhomAndGVs(rs.getInt(1),giangVien);
                    boolean add = true;
                    for (var bh : bhs)
                        if (!thu.contains(HocPhan.dayToByte(bh.thu()))) {
                            add = false;
                            break;
                        }
                    if (add) dsHP.add(new HocPhanDTO(
                            rs.getString("ma_mh"),
                            rs.getString("ten"),
                            rs.getByte("so_tin_chi"),
                            rs.getString("so"),
                            rs.getString("to_th"),
                            rs.getInt("si_so"),
                            rs.getInt("con_lai"),
                            bhs
                    ));
                }
                rs.close();
                statement.close();
            } catch (SQLException ignored) {
            }
            dsHPbyMH.add(dsHP);
        }
        return dsHPbyMH;
    }

}
