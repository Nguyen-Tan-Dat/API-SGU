package net.sgu.api.models;

import net.sgu.api.models.dto.BuoiHoc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BuoiHocDAO {
    private static List<String[]> data;

    public BuoiHocDAO() {
        getData();
    }

    public static List<String[]> getData() {
        if (data == null) data = new ArrayList<>();
        return data;
    }

    public static List<BuoiHoc> select(String query) {
        List<BuoiHoc> buoiHocs = new ArrayList<>();
        try {
            PreparedStatement statement = Database.getConnect().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int maNhom = resultSet.getInt("ma_nhom");
                boolean thucHanh = resultSet.getBoolean("thuc_hanh");
                String thu = resultSet.getString("thu");
                byte tietBD = resultSet.getByte("tiet_bd");
                byte soTiet = resultSet.getByte("so_tiet");
                String phong = resultSet.getString("phong");
                String giangVien = resultSet.getString("giang_vien");
                String tuan = resultSet.getString("tuan");
                BuoiHoc buoiHoc = new BuoiHoc(maNhom, thucHanh, thu, tietBD, soTiet, phong, giangVien, tuan);
                buoiHocs.add(buoiHoc);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ignored) {
        }
        return buoiHocs;
    }

    public static List<BuoiHoc> getByNhomAndThus(int ma, Set<Byte> thu) {
        String query="SELECT * FROM buoi_hoc WHERE ma_nhom="+ma+" and (";
        for(Byte i:thu){
            query+="thu='"+HocPhan.byteToDay(i.byteValue())+"' or ";
        }
        query=query.substring(0, query.length() - 3)+");";
        System.out.println(query);
        return select(query);
    }
    public static List<BuoiHoc> getByNhomAndGVs(int ma, Set<String> gv) {
        String query="SELECT * FROM buoi_hoc WHERE ma_nhom="+ma+" and (";
        for(String i:gv){
            query+="giang_vien='"+i+"' or ";
        }
        query=query.substring(0, query.length() - 3)+");";
        System.out.println(query);
        return select(query);
    }

    public static List<BuoiHoc> getByNhomAndThusAndGVs(int ma,Set<Byte>thu, Set<String> gv) {
        String query = "SELECT * FROM buoi_hoc WHERE ma_nhom=" + ma + " and (";
        for (String i : gv) {
            query += "giang_vien='" + i + "' or ";
        }
        query = query.substring(0, query.length() - 3) + ") and (";
        for(Byte i:thu){
            query+="thu='"+HocPhan.byteToDay(i.byteValue())+"' or ";
        }
        query = query.substring(0, query.length() - 3) + ");";
        System.out.println(query);
        return select(query);
    }

    public List<BuoiHoc> getAll() {
        return select("SELECT * FROM buoi_hoc");
    }
    public static List<BuoiHoc> getByNhom(int ma) {
        return select("SELECT * FROM buoi_hoc WHERE ma_nhom="+ma);
    }
    public boolean insert(BuoiHoc buoiHoc) {
        try {
            String sql = "INSERT INTO buoi_hoc (ma_nhom, thuc_hanh, thu, tiet_bd, so_tiet, phong, giang_vien, tuan) VALUES (?, ?, ?, ?, ?, ?,?, ?)";
            PreparedStatement statement = Database.getConnect().prepareStatement(sql);
            statement.setInt(1, buoiHoc.maNhom());
            statement.setBoolean(2, buoiHoc.thucHanh());
            statement.setString(3, buoiHoc.thu());
            statement.setByte(4, buoiHoc.tietBD());
            statement.setByte(5, buoiHoc.soTiet());
            statement.setString(6, buoiHoc.phong());
            statement.setString(7, buoiHoc.giangVien());
            statement.setString(8, buoiHoc.tuan());
            statement.executeUpdate();
            statement.close();
            return true;
        } catch (SQLException ignored) {
            System.out.println(buoiHoc);
            return false;
        }
    }

    public void clear() {
        try {
            String sql = "DELETE FROM buoi_hoc";
            PreparedStatement statement = Database.getConnect().prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ignored) {
        }
    }

}

