package j241018;

import java.io.File;

public class FileClassExample {
	public static void listDirectory(File dir) {
		System.out.println("-----" + dir.getPath() + "의 서브 리스트 입니다.-----");
		
		File[] subFiles = dir.listFiles();
		
		for (int i = 0; i < subFiles.length; i++) {
			
			File f = subFiles[i];
			long t = f.lastModified(); // 마지막으로 수정된 시간
			
			System.out.print(f.getName());
			System.out.print("\t파일 크기: " + f.length()); // 파일 크기
			System.out.printf("\t수정한 시간: %tb %td %ta %tT\n", t, t, t, t);
		}
	}

	public static void main(String[] args) {
		File f1 = new File("c:\\windows\\system.ini");
		System.out.println(f1.getPath() + ", " + f1.getParent() + ", " + f1.getName());
		
		String res = "";
		if (f1.isFile())
			res = "파일";
		else if (f1.isDirectory()) //디렉토리 인지 확인 
			res = "디렉토리";
		System.out.println(f1.getPath() + "은 " + res + "입니다.");
		
		File f2 = new File("c:\\Temp\\java_sample");
		if (!f2.exists()) { // 파일이 존재하는지 확인 
			f2.mkdir();  //mkdir 새로운 디렉토리 생성 
		}
		listDirectory(new File("c:\\Temp"));
		f2.renameTo(new File("c:\\Temp\\javasample")); //파일의 이름을 수정
		listDirectory(new File("c:\\Temp"));

	}

}
