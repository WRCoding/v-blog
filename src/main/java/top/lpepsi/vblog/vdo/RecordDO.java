package top.lpepsi.vblog.vdo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: v-blog
 * @description: 保存记录DO
 * @author: 林北
 * @create: 2020-05-01 20:50
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDO implements Serializable {
    private String userName;
    private Date createBy;
    private Integer data;

    public RecordDO(String userName, Integer data) {
        this.userName = userName;
        this.data = data;
    }

    public RecordDO(Date createBy, Integer data) {
        this.createBy = createBy;
        this.data = data;
    }
}
