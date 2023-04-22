package net.sgu.api.models.dto;

public record BuoiHoc(int maNhom, boolean thucHanh, String thu, byte tietBD, byte soTiet, String phong,
                      String giangVien, String tuan) {
}