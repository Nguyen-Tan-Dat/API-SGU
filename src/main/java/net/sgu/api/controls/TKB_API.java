package net.sgu.api.controls;

import net.sgu.api.models.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("tkb")
public class TKB_API {

    @GetMapping("realtime")
    @CrossOrigin(origins = "*")
    public List<List<ThoiKhoaBieu>> makeTKB(@RequestParam("dsMH") List<String> dsMaMH) {
        return make(dataOnline(dsMaMH));
    }

    @GetMapping("realtime/dsHP")
    @CrossOrigin(origins = "*")
    public List<List<HocPhan>> dataOnline(@RequestParam("dsMH") List<String> infos) {
        DataWeb web = new DataWeb();
        List<List<HocPhan>> rs = new ArrayList<>();
        if (infos.size() < 1) return rs;
        for (var i : infos) {
            rs.add(web.getData(i));
        }
        web.close();
        return rs;
    }

    @GetMapping("normal")
    @CrossOrigin(origins = "*")
    public List<List<List<HocPhanDTO>>> normalMakeTKB(@RequestParam("dsMH") List<String> dsMaMH) {
        return normalMake(HocPhanDAO.dsHPbyMH(dsMaMH));
    }
    public List<List<ThoiKhoaBieu>> make(List<List<HocPhan>> arr) {
        List<List<ThoiKhoaBieu>> result = new ArrayList<>();
        for (int i = 0; i < 6; i++)
            result.add(new ArrayList<>());
        try {
            int n = arr.size();
            int[] pos = new int[n];
            while (true) {
                List<HocPhan> dsHocPhan = new ArrayList<>();
                for (int i = 0; i < n; i++)
                    dsHocPhan.add(arr.get(i).get(pos[i]));
                ThoiKhoaBieu tkb = new ThoiKhoaBieu(dsHocPhan);
                if (tkb.hopLe()) result.get(tkb.soNgayDiHoc() - 1).add(tkb);
                int i = n - 1;
                while (i >= 0 && pos[i] == arr.get(i).size() - 1) i--;
                if (i < 0) break;
                pos[i]++;
                for (int j = i + 1; j < n; j++) pos[j] = 0;
            }
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public List<List<List<HocPhanDTO>>> normalMake(List<List<HocPhanDTO>> arr) {
        List<List<List<HocPhanDTO>>> result = new ArrayList<>();
        for (int i = 0; i < 7; i++)
            result.add(new ArrayList<>());
        try{
        int n = arr.size();
        int[] pos = new int[n];
        while (true) {
            List<HocPhanDTO> dsHocPhan = new ArrayList<>();
            for (int i = 0; i < n; i++)
                dsHocPhan.add(arr.get(i).get(pos[i]));
            if (ThoiKhoaBieu.isTKB(dsHocPhan)) result.get(ThoiKhoaBieu.soNgayDiHoc(dsHocPhan) - 1).add(dsHocPhan);
            int i = n - 1;
            while (i >= 0 && pos[i] == arr.get(i).size() - 1) i--;
            if (i < 0) break;
            pos[i]++;
            for (int j = i + 1; j < n; j++) pos[j] = 0;
        }}catch (Exception e){
            return null;
        }
        return result;
    }

    @GetMapping("/realtime/info/{info}")
    @CrossOrigin(origins = "*")
    public List<HocPhan> hi(@PathVariable String info) {
        DataWeb web = new DataWeb();
        var rs = web.getData(info);
        web.close();
        return rs;
    }
}
