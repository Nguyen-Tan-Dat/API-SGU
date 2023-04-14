package net.sgu.api.models;

import net.sgu.api.models.dto.Nhom;
import net.sgu.api.models.dto.NhomHasNameMH;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhomDAO {


    public void insert(Nhom nhom) {
        try {
            String sql = "INSERT INTO nhom (ma,ma_mh, so, to_th, si_so, con_lai) VALUES (?, ?, ?, ?, ?,?)";
            PreparedStatement statement = Database.getConnect().prepareStatement(sql);
            statement.setInt(1, nhom.ma());
            statement.setString(2, nhom.maMH());
            statement.setString(3, nhom.so());
            statement.setString(4, nhom.toTH());
            statement.setShort(5, nhom.siSo());
            statement.setShort(6, nhom.conLai());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException ignored) {
        }
    }

    public void clear() {
        Database.clear("nhom");
    }

    public Nhom getByMa(int ma) {
        Nhom nhom = null;
        try {
            String sql = "SELECT * FROM nhom WHERE ma = ?";
            PreparedStatement statement = Database.getConnect().prepareStatement(sql);
            statement.setInt(1, ma);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String maMH = resultSet.getString("ma_mh");
                String so = resultSet.getString("so");
                String toTH = resultSet.getString("to_th");
                short siSo = resultSet.getShort("si_so");
                short conLai = resultSet.getShort("con_lai");
                nhom = new Nhom(ma, maMH, so, toTH, siSo, conLai);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ignored) {
        }
        return nhom;
    }
    public NhomHasNameMH getByMaHasNameMH(int ma) {
        NhomHasNameMH nhom = null;
        try {
            String sql = "select n.*,m.ten from nhom n,mon_hoc m where n.ma=? and n.ma_mh=m.ma;";
            PreparedStatement statement = Database.getConnect().prepareStatement(sql);
            statement.setInt(1, ma);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String maMH = resultSet.getString("ma_mh");
                String so = resultSet.getString("so");
                String toTH = resultSet.getString("to_th");
                short siSo = resultSet.getShort("si_so");
                short conLai = resultSet.getShort("con_lai");
                String tenMH=resultSet.getString("ten");
                nhom=new NhomHasNameMH(ma, maMH, so, toTH, siSo, conLai,tenMH);
                System.out.println(nhom);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ignored) {
        }
        return nhom;
    }

    public List<Nhom> select(String query) {
        List<Nhom> list = new ArrayList<>();
        try {
            PreparedStatement statement = Database.getConnect().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int ma = resultSet.getInt("ma");
                String maMH = resultSet.getString("ma_mh");
                String so = resultSet.getString("so");
                String toTH = resultSet.getString("to_th");
                short siSo = resultSet.getShort("si_so");
                short conLai = resultSet.getShort("con_lai");
                Nhom nhom = new Nhom(ma, maMH, so, toTH, siSo, conLai);
                list.add(nhom);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ignored) {
        }
        return list;
    }
    public List<NhomHasNameMH> selectHasTenMH(String query) {
        List<NhomHasNameMH> list = new ArrayList<>();
        try {
            PreparedStatement statement = Database.getConnect().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int ma = resultSet.getInt("ma");
                String maMH = resultSet.getString("ma_mh");
                String so = resultSet.getString("so");
                String toTH = resultSet.getString("to_th");
                short siSo = resultSet.getShort("si_so");
                short conLai = resultSet.getShort("con_lai");
                String tenMH=resultSet.getString("ten");
                list.add(new NhomHasNameMH(ma, maMH, so, toTH, siSo, conLai,tenMH));
                System.out.println(list.get(list.size()-1));
            }
            resultSet.close();
            statement.close();
        } catch (SQLException ignored) {
        }
        return list;
    }

    public List<Nhom> all() {
        return select("SELECT * FROM nhom;");
    }
    public List<NhomHasNameMH> allHasNameMH() {
        return selectHasTenMH("select n.*,m.ten from nhom n,mon_hoc m where n.ma_mh=m.ma;");
    }

    public List<NhomHasNameMH> getByMaMHHasNameMH(String maMH) {
        return selectHasTenMH("select n.*,m.ten from nhom n,mon_hoc m where n.ma_mh= '" + maMH + "' and n.ma_mh=m.ma");
    }
    public List<Nhom> getByMaMH(String maMH) {
        return select("SELECT * FROM nhom WHERE ma_mh = '" + maMH + "';");
    }

}