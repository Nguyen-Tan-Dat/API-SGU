package net.sgu.api.controls;
import net.sgu.api.models.*;
import net.sgu.api.models.dto.BuoiHoc;
import net.sgu.api.models.dto.MonHoc;
import net.sgu.api.models.dto.Nhom;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.*;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("update")
public class UpdateAPI {
    private final MonHocDAO mhd;
    private final NhomDAO nd;
    private final BuoiHocDAO bhd;

    public UpdateAPI() {
        this.mhd = new MonHocDAO();
        this.nd = new NhomDAO();
        this.bhd = new BuoiHocDAO();
        autoUpdate();
    }

    private static double updating = 0;

    @GetMapping("count")
    @CrossOrigin(origins = "*")
    public String countUpdate(){
        return countUpdate+" update";
    }
    @GetMapping("start")
    @CrossOrigin(origins = "*")
    public String startUpdate() {
        if (updating == 0) {
            updating = 0.00001;
            new Thread(this::update).start();
        }
        DecimalFormat df = new DecimalFormat("##.##");
        return "Đã cập nhật " + df.format(updating * 100) + "%";
    }

    @GetMapping("auto-update-on/{on}")
    @CrossOrigin(origins = "*")
    public String setupAutoUpdate(@PathVariable boolean on) {
        if(on){
            autoUpdate=true;
            autoUpdate();
            return "complete on";
        }
        else{
            autoUpdate=false;
            return "complete off";
        }
    }

    private List<String> listHeadId() {
        List<String> infos = new ArrayList<>();
        for (int i = 801; i < 868; i++) {
            infos.add(String.valueOf(i));
        }
        infos.add("BO");
        infos.add("CA");
        return infos;
    }

    private void merge(List<String[]> data) {
        for (var i : data) {
            mhd.merge(new MonHoc(i[1], i[2], Byte.parseByte(i[5])));
            dsMaMH.add(i[1]);
        }
    }

    private HashSet<String> dsMaMH;
    private int maNhom = 0;

    public void update() {
        bhd.clear();
        nd.clear();
        mhd.clear();
        var infos = listHeadId();
        dsMaMH = new HashSet<>();
        DataWeb dataweb = new DataWeb();
        for (int i = 0; i < infos.size(); i++) {
            dataweb.requestTDK(infos.get(i));
            merge(dataweb.fromTDKtoData(dataweb.getTDK()));
            updating = Math.round(((double) (i) / infos.size()) * 10000.0) / 10000.0;
        }
        int c=0;
        for (var i:dsMaMH) {
            dataweb.requestTDK(i);
            updateNhom(dataweb.fromTDKtoData(dataweb.getTDK()));
            updating = Math.round(((double) (++c) / dsMaMH.size()) * 10000.0) / 10000.0;
        }
        dataweb.close();
        try {
            updating = 1;
            sleep(15 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updating = 0;
        countUpdate++;
    }

    private void updateNhom(List<String[]> data) {
        for (var i : data) {
            short conlai = 0, siso = 0;
            try {
                siso = Short.parseShort(i[8]);
                conlai = Short.parseShort(i[9]);
            } catch (Exception ignored) {
            }
            nd.insert(new Nhom(++maNhom, i[1], i[3], i[4], siso, conlai));
            String[] thu = i[11].split(" ");
            String[] bd = i[12].split(" ");
            String[] st = i[13].split(" ");
            String[] phong = i[14].split(" ");
            String[] gv = i[15].split(" ");
            String[] tuan = i[16].split(" ");
            for (int s = 0; s < thu.length; s++) {
                String gvs = getData(gv, s);
                String thus = getData(thu, s);
                byte bds = 0, sts = 0;
                try {
                    bds = Byte.parseByte(getData(bd, s));
                    sts = Byte.parseByte(getData(st, s));
                } catch (Exception ignored) {
                }
                String phongs = getData(phong, s);
                String tuans = getData(tuan, s);
                BuoiHoc lh = new BuoiHoc(maNhom, false, thus, bds, sts, phongs, gvs, tuans);
                bhd.insert(lh);
            }
        }
    }

    private String getData(String[] a, int i) {
        try {
            return a[i];
        } catch (Exception e) {
            return "";
        }
    }
    private boolean autoUpdate=false;
    private static int countUpdate=0;
    public void autoUpdate() {
        if(!autoUpdate) return;
        autoUpdate=true;
        new Thread(() -> {
            TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
            Timer timer = new Timer();
            Calendar startDate = Calendar.getInstance();
            startDate.set(Calendar.HOUR_OF_DAY, 3);
            startDate.set(Calendar.MINUTE, 0);
            startDate.set(Calendar.SECOND, 0);
            if (startDate.getTimeInMillis() < System.currentTimeMillis()) {
                startDate.add(Calendar.DAY_OF_MONTH, 1);
            }
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    if(autoUpdate){
                        update();
                    }
                    else{
                        cancel();
                    }
                }
            };
            timer.schedule(task, startDate.getTime(), 24 * 60 * 60 * 1000);
            try {
                sleep(startDate.getTimeInMillis() - System.currentTimeMillis());
            } catch (InterruptedException ignored) {
            }
        }).start();
    }
}