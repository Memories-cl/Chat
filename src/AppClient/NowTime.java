package AppClient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NowTime { 
	public static String getTime() { 
       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
       return (df.format(new Date()));// new Date()为获取当前系统时间
  }
}

