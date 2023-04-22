package net.sgu.api.controls;

import net.sgu.api.models.dto.MonHoc;
import net.sgu.api.models.MonHocDAO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mon-hoc")
public class MonHocAPI {
    private final MonHocDAO dao;

    public MonHocAPI() {
        dao = new MonHocDAO();
    }

    @GetMapping("all")
    @CrossOrigin(origins = "*")
    public List<MonHoc> getList() {
        return MonHocDAO.getAll();
    }

    @GetMapping("id/{ma}")
    @CrossOrigin(origins = "*")
    public MonHoc getMonHoc(@PathVariable String ma) {
        return dao.getById(ma);
    }

}
