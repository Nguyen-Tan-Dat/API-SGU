DROP TABLE IF EXISTS buoi_hoc;
DROP TABLE IF EXISTS nhom;
DROP TABLE IF EXISTS mon_hoc;
CREATE TABLE mon_hoc
(
    ma         VARCHAR(6) PRIMARY KEY,
    ten        NVARCHAR(100)    NOT NULL,
    so_tin_chi TINYINT UNSIGNED NOT NULL
);

CREATE TABLE nhom
(
    ma      INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    ma_mh   VARCHAR(6)        NOT NULL,
    so      VARCHAR(2)        NOT NULL,
    to_th   VARCHAR(2)        NOT NULL,
    si_so   SMALLINT UNSIGNED NOT NULL,
    con_lai SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (ma_mh) REFERENCES mon_hoc (ma) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE UNIQUE INDEX idx_nhom_ma_mh_so_to_th ON nhom (ma_mh, so, to_th);

CREATE TABLE buoi_hoc
(
    ma_nhom    INT UNSIGNED     NOT NULL,
    thuc_hanh  BIT(1)           NOT NULL,
    thu        NVARCHAR(3)      NOT NULL,
    tiet_bd    TINYINT UNSIGNED NOT NULL,
    so_tiet    TINYINT UNSIGNED NOT NULL,
    phong      VARCHAR(10),
    giang_vien NVARCHAR(255),
    tuan       VARCHAR(20)      NOT NULL,
    PRIMARY KEY (ma_nhom, thu, giang_vien, tiet_bd),
    FOREIGN KEY (ma_nhom) REFERENCES nhom (ma) ON UPDATE CASCADE ON DELETE CASCADE
);
SELECT MAX(ma)
FROM nhom