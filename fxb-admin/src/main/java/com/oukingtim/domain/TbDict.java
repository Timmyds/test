package com.oukingtim.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by oukingtim
 */
@TableName("tb_dict")
@Data
@EqualsAndHashCode(callSuper = false)
public class TbDict extends BaseModel<TbDict> {

    private String code;
    private String text;
    private String remark;
    private String dictClassId;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDictClassId() {
		return dictClassId;
	}
	public void setDictClassId(String dictClassId) {
		this.dictClassId = dictClassId;
	}

}
