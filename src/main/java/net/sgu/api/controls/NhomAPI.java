package net.sgu.api.controls;

import net.sgu.api.models.NhomDAO;
import net.sgu.api.models.dto.NhomHasNameMH;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("nhom")
public class NhomAPI {
    private final NhomDAO dao;

    public NhomAPI() {
        this.dao = new NhomDAO();
    }

    @GetMapping("/all")
    @CrossOrigin(origins = "*")
    public List<NhomHasNameMH> getList() {
        return dao.allHasNameMH();
    }

    @GetMapping("/id/{ma}")
    @CrossOrigin(origins = "*")
    public NhomHasNameMH byID(@PathVariable int ma) {
        return dao.getByMaHasNameMH(ma);
    }

    @GetMapping("/mon-hoc/{ma}")
    @CrossOrigin(origins = "*")
    public List<NhomHasNameMH> byMonHoc(@PathVariable String ma) {
        return dao.getByMaMHHasNameMH(ma);
    }

}
