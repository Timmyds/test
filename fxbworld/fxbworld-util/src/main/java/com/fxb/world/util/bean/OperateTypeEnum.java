package com.fxb.world.util.bean;
/**
 *炒作方式
 * @author ds
 *
 */
public enum OperateTypeEnum {
	

	
	//pc
	pc{@Override
			public Integer getSign(){return 1;}
	},
	//
	app{@Override
			public Integer getSign(){return 2;}
	};
	public abstract   Integer getSign();
	
	
}
