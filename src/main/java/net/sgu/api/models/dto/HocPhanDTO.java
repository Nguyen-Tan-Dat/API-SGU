package net.sgu.api.models.dto;

import java.util.List;

public record HocPhanDTO(String maMH, String tenMH, byte stc, String nhom, String tth, int siSo, int conLai,
                         List<BuoiHoc> buoiHoc) {
}
