package net.sgu.api.models;

import net.sgu.api.models.dto.MonHoc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MonHocDAO {
    public static List<MonHoc> getAll() {
        List<MonHoc> data = new ArrayList<>();
        try {
            Statement stmt = Database.getConnect().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM mon_hoc");
            while (rs.next()) {
                data.add(new MonHoc(
                        rs.getString("ma"),
                        rs.getString("ten"),
                        rs.getByte("so_tin_chi")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void merge(MonHoc mh) {
        try {
            PreparedStatement stmt = Database.getConnect().prepareStatement(
                    "INSERT INTO mon_hoc (ma, ten, so_tin_chi) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE ten= ?,so_tin_chi = ?;"
            );
            stmt.setString(1, mh.ma());
            stmt.setString(2, mh.ten());
            stmt.setByte(3, mh.soTinChi());
            stmt.setString(4, mh.ten());
            stmt.setByte(5, mh.soTinChi());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        try {
            String sql = "DELETE FROM mon_hoc";
            PreparedStatement statement = Database.getConnect().prepareStatement(sql);
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static MonHoc getById(String ma) {
        try {
            String sql = "SELECT * FROM mon_hoc WHERE ma = ?";
            PreparedStatement statement = Database.getConnect().prepareStatement(sql);
            statement.setString(1, ma);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return new MonHoc(rs.getString(1), rs.getString(2), rs.getByte(3));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
