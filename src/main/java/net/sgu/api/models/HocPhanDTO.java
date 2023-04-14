package net.sgu.api.models;

import java.util.List;

public class HocPhanDTO {
    private int maNhom;
    private List<byte[]> times;

    public HocPhanDTO(int maNhom, List<byte[]> times) {
        this.maNhom = maNhom;
        this.times = times;
    }

    public int getMaNhom() {
        return maNhom;
    }

    public List<byte[]> getTimes() {
        return times;
    }
}
