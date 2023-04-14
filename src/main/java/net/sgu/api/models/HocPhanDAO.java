package net.sgu.api.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HocPhanDAO {

    public HocPhanDAO() {
    }
    public static List<List<HocPhanDTO>> dsHPbyMH(List<String> dsMaMH){
        List<List<HocPhanDTO>> dsHPbyMH=new ArrayList<>();
        for(var i:dsMaMH){
            List<HocPhanDTO> dsHP=new ArrayList<>();
            try {
                PreparedStatement statement = Database.getConnect().prepareStatement("SELECT ma FROM nhom WHERE ma_mh='"+i+"' AND con_lai>0;");
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    int maNhom=rs.getInt(1);
                    List<byte[]> times=new ArrayList<>();
                    try {
                        PreparedStatement st = Database.getConnect().prepareStatement("SELECT thu,tiet_bd,so_tiet FROM buoi_hoc WHERE ma_nhom='"+maNhom+"'");
                        ResultSet rsc = st.executeQuery();
                        while (rsc.next()) {
                            times.add(new byte[]{HocPhan.convertDay(rsc.getString(1)),rsc.getByte(2),rsc.getByte(3)});
                        }
                        rsc.close();
                        st.close();
                    } catch (SQLException ignored) {
                    }
                    dsHP.add(new HocPhanDTO(maNhom,times));
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
