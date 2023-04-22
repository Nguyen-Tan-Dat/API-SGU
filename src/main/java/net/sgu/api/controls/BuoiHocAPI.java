package net.sgu.api.controls;

import net.sgu.api.models.dto.BuoiHoc;
import net.sgu.api.models.BuoiHocDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("buoi-hoc")
public class BuoiHocAPI {
    private final BuoiHocDAO dao;

    public BuoiHocAPI() {
        this.dao = new BuoiHocDAO();
    }

    @GetMapping("all")
    @CrossOrigin(origins = "*")
    public List<BuoiHoc> all() {
        return dao.getAll();
    }

    @GetMapping("nhom/{ma}")
    @CrossOrigin(origins = "*")
    public List<BuoiHoc> byNhom(@PathVariable int ma) {
        return dao.getByNhom(ma);
    }

}
