package AppClient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NowTime { 
	public static String getTime() { 
       SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//�������ڸ�ʽ
       return (df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ��
  }
}

