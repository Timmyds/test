package com.oukingtim.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by oukingtim
 */
@TableName("tb_dict_class")
@Data
@EqualsAndHashCode(callSuper = false)
public class TbDictClass extends BaseModel<TbDictClass> {

    private String code;
    private String remark;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

}
