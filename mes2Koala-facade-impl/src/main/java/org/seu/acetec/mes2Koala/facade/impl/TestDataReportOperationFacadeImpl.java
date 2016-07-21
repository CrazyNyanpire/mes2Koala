package org.seu.acetec.mes2Koala.facade.impl;

import help.FilenameHelper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.SocketException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.openkoala.koala.commons.InvokeResult;
import org.seu.acetec.mes2Koala.application.CPLotApplication;
import org.seu.acetec.mes2Koala.application.CPLotNodeOperationApplication;
import org.seu.acetec.mes2Koala.application.CPProcessApplication;
import org.seu.acetec.mes2Koala.application.ProductionScheduleApplication;
import org.seu.acetec.mes2Koala.core.domain.CPLot;
import org.seu.acetec.mes2Koala.facade.TestDataReportOperationFacade;
import org.seu.acetec.mes2Koala.facade.dto.TSKInfoDTO;
import org.seu.acetec.mes2Koala.facade.dto.TestData3360InfoDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.tools.ant.Project;    
import org.apache.tools.ant.taskdefs.Zip;    
import org.apache.tools.ant.types.FileSet; 

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

/**
 * @author HongYu
 * @version 2016/6/7
 */

public class TestDataReportOperationFacadeImpl implements TestDataReportOperationFacade {
	
//    private static final  String server = "192.168.60.16";
//    //private static final  String server = "192.168.1.35";
//    private static final  String ftpUserName = "file001";
//    private static final  String ftpUserPwd = "admin001";
//    //private static final  String sumryServer = "192.168.1.36";
//    //private static final  String sumryFtpUserName = "tde001";
//    //private static final  String sumryFtpUserPwd = "tde001";
//    private static final  String sumryServer = "192.168.60.15";
//    private static final  String sumryFtpUserName = "file002";
//    private static final  String sumryFtpUserPwd = "admin002";
	private String server;
	private String ftpUserName;
	private String ftpUserPwd;
	private String sumryServer;
	private String sumryFtpUserName;
	private String sumryFtpUserPwd;
    private static final  String localDirectory = "D:/TSK/";
    private String lastLotId = "";
    private String hynfilename = "";
    private long bin1_total;
    private long bin2_total;
    private long bin3_total;
    @Inject
    CPLotNodeOperationApplication cpLotNodeOperationApplication;
    @Inject
    CPLotApplication cpLotApplication;
    @Inject
    CPProcessApplication cpProcessApplication;
    @Inject
    ProductionScheduleApplication productionScheduleApplication;

	@Override
	public InvokeResult getTskFileNames(String upDown,String directory) {
		 FTPClient ftpClient = new FTPClient();
		 List fileLists = new ArrayList();
		 Map<String, Object> map = new HashMap<>();
		 List timeLists = new ArrayList();
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 try {
			ftpClient.connect(server);
			ftpClient.login(ftpUserName, ftpUserPwd);
			ftpClient.setDefaultTimeout(15000);
		    ftpClient.setSoTimeout(30000);
			ftpClient.setDataTimeout(15000);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
			    throw new RuntimeException("连接FTP服务器失败!");
			}
            ftpClient.changeWorkingDirectory("/" + "map"+ "/" + upDown);
            FTPFile[] ftpFiles = ftpClient.listFiles();
			for (int i = 0 ; i < ftpFiles.length ; i++) {
				FTPFile file = ftpFiles[i];
				if (file.isDirectory() ) {
					if (directory == null || directory == "") {
						//不知为何加上 && !file.getName().contains(".") 暂时给注释掉
						if (!file.getName().contains("..")) {
							fileLists.add(file.getName());
							Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							cal.setTime((Date)(file.getTimestamp().getTime()));
							timeLists.add(df.format(cal.getTime()));
						}
					} else {
						//不知为何加上 && !file.getName().contains(".") 暂时给注释掉
						if (!file.getName().contains("..")  
								&& file.getName().contains(directory)) {
							fileLists.add(file.getName());
							Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							cal.setTime((Date)(file.getTimestamp().getTime()));
							timeLists.add(df.format(cal.getTime()));
						}
					}
				}
			}
			map.put("directoryName", fileLists);
			map.put("timeStamp", timeLists);
			return InvokeResult.success(map);
		} catch (SocketException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}
	
	@Override
	public InvokeResult resolveFile(String upDown,String directoryName) {
		 FTPClient ftpClient = new FTPClient();
         List<TSKInfoDTO> tskinfos = new ArrayList<TSKInfoDTO>();
         FileInputStream fileIn = null;
         DataInputStream in = null;
		 try {
			ftpClient.connect(server);
			ftpClient.login(ftpUserName, ftpUserPwd);
			ftpClient.setDefaultTimeout(15000);
		    ftpClient.setSoTimeout(30000);
			ftpClient.setDataTimeout(15000);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
			    throw new RuntimeException("连接FTP服务器失败!");
			}
            ftpClient.changeWorkingDirectory("/" + "map"+ "/" + upDown +  "/" + directoryName);
            FTPFile[] ftpFiles = ftpClient.listFiles();
			for (int i = 0 ; i < ftpFiles.length ; i++) {
				TSKInfoDTO tskinfo = new TSKInfoDTO();
				FTPFile file = ftpFiles[i];
				if (!file.getName().endsWith(".xtr") && !file.getName().endsWith(".inf") 
						&& !file.getName().contains("=")
						&& !file.getName().endsWith(".BCT") && !file.getName().endsWith(".DAT") ) {
					File folder = new File(localDirectory + upDown + "/" + directoryName);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					ftpClient.retrieveFile( new String(file.getName().getBytes(), "iso-8859-1"), 
							new DataOutputStream(new FileOutputStream(localDirectory + upDown + "/" + directoryName + "/" +file.getName())));
					File f = new File(localDirectory + upDown + "/" + directoryName + "/" +file.getName());
					tskinfo.setFileName(file.getName());
					byte[] readBuff = new byte[(int)f.length()];
					fileIn = new FileInputStream(localDirectory + upDown + "/" + directoryName + "/" +file.getName());
				    in = new DataInputStream(fileIn); 
				    in.read(readBuff);
				    String operator_Name = getByte(readBuff,1,20);  
				    String device_Name = getByte(readBuff,21,36);
				    int Wafer_Size = getIntBinary(readBuff,37,38);
				    int Machine_No = getIntBinary(readBuff,39,40);
				    int index_Size_X = getIntBinary(readBuff,41,44);
				    int index_Size_Y = getIntBinary(readBuff,45,48);
				    int standard_Orientation_Flat_Direction = getIntBinary(readBuff,49,50);
				    int final_Editing_Machine = getIntBinary(readBuff,51,51);
				    int map_Version = getIntBinary(readBuff,52,52);
				    long columnsize = getIntBinary(readBuff,53,54);
				    long rowsize = getIntBinary(readBuff,55,56);
				    if ("P7241D".equals(device_Name))
		            {
		                columnsize += 2L;
		                rowsize += 2L;
		            }
				    int map_Data_Form = getIntBinary(readBuff,57,60);
				    String wafer_ID = getByte(readBuff,61,81);
				    int number_of_Probing = getIntBinary(readBuff,82,82);
				    String lot_No = getByte(readBuff,83,100);
				    int cassette_No = getIntBinary(readBuff,101,102);
				    int slot_No = getIntBinary(readBuff,103,104);
				    int x_axis_coordinates_increase_direction = getIntBinary(readBuff,105,105);
				    int y_axis_coordinates_increase_direction = getIntBinary(readBuff,106,106);
				    int reference_die_setting_procedures = getIntBinary(readBuff,107,107);
				    int reserved_108 = getIntBinary(readBuff,108,108);
				    int target_die_position_X = getIntBinary(readBuff,109,112);
				    int target_die_position_Y = getIntBinary(readBuff,113,116);
				    int refdieX = getIntBinary(readBuff,117,118);
				    if (refdieX > 20)
		            {
		                refdieX = 1;
		            }
				    int refdieY = getIntBinary(readBuff,119,120);
				    int probing_start_position = getIntBinary(readBuff, 121, 121);
				    int probing_direction = getIntBinary(readBuff, 122, 122);
				    int reserved_123_124 = getIntBinary(readBuff, 123, 124);
				    int distance_X_to_wafer_center_die_origin = getIntBinary(readBuff, 125, 128);
				    int distance_Y_to_wafer_center_die_origin = getIntBinary(readBuff, 129, 132);
				    int coordinator_X_of_wafer_center_die = getIntBinary(readBuff, 133, 136);
				    int coordinator_Y_of_wafer_center_die = getIntBinary(readBuff, 137, 140);
				    int first_Die_Coordinator_X = getIntBinary(readBuff, 141, 144);
				    String startTime_Year = getByte(readBuff, 149, 150);
				    String startTime_Month = getByte(readBuff, 151, 152);
				    String startTime_Day = getByte(readBuff, 153, 154);
				    String startTime_Hour = getByte(readBuff, 155, 156);
				    String startTime_Minute = getByte(readBuff, 157, 158);
				    int startTime_Reserve = getIntBinary(readBuff, 159, 160);
				    String endTime_Year = getByte(readBuff, 161, 162);
				    String endTime_Month = getByte(readBuff, 163, 164);
				    String endTime_Day = getByte(readBuff, 165, 166);
				    String endTime_Hour = getByte(readBuff, 167, 168);
				    String endTime_Minute = getByte(readBuff, 169, 170);
				    int endTime_Reserve = getIntBinary(readBuff, 171, 172);
		            String loadTime_Year = getByte(readBuff, 173, 174);
		            String loadTime_Month = getByte(readBuff, 175, 176);
		            String loadTime_Day = getByte(readBuff, 177, 178);
		            String loadTime_Hour = getByte(readBuff, 179, 180);
		            String loadTime_Minute = getByte(readBuff, 181, 182);
		            int loadTime_Reserve = getIntBinary(readBuff, 183, 184);
		            String unLoadTime_Year = getByte(readBuff, 185, 186);
		            String unLoadTime_Month = getByte(readBuff, 187, 188);
		            String unLoadTime_Day = getByte(readBuff, 189, 190);
		            String unLoadTime_Hour = getByte(readBuff, 191, 192);
		            String unLoadTime_Minute = getByte(readBuff, 193, 194);
		            int unLoadTime_Reserve = getIntBinary(readBuff, 195, 196);
		            int machine_No_1 = getIntBinary(readBuff, 197, 200);
		            int machine_No_2 = getIntBinary(readBuff, 201, 204);
		            int special_Characters = getIntBinary(readBuff, 205, 208);
		            int testing_End_Information = getIntBinary(readBuff, 209, 209);
		            int reserve_210 = getIntBinary(readBuff, 210, 210);
		            int tested_dice = getIntBinary(readBuff, 211, 212);
		            int tested_pass_dice = getIntBinary(readBuff, 213, 214);
		            int tested_fail_dice = getIntBinary(readBuff, 215, 216);
		            int test_Die_Information_Address = getIntBinary(readBuff, 217, 220);
		            int number_of_line_category_data = getIntBinary(readBuff, 221, 224);
		            int line_category_address = getIntBinary(readBuff, 225, 228);
		            String map_File_Configuration = byteToBinary(readBuff, 229, 230);
		            int max_Multi_Site = getIntBinary(readBuff, 231, 232);
		            int max_Categories = getIntBinary(readBuff, 233, 234);
		            int reserve_235236 = getIntBinary(readBuff, 235, 236);
		            int nTotalChips = (int)(columnsize * rowsize);
		            int Byte_of_Header_Information = 236;
		            int Byte_of_Extended_Header_Information = 172;

		            // Map_File_Configuration
		            int availability_of_Header_Information = mid(map_File_Configuration, 16, 17);
		            int availability_of_Test_Result_Information_per_Die = mid(map_File_Configuration, 15, 16);
		            int availability_of_Line_Category_Information = mid(map_File_Configuration, 14, 15);
		            int availability_of_Extension_Header_Information = mid(map_File_Configuration, 13, 14);
		            int availability_of_Test_Result_Information_per_Extension_Die = mid(map_File_Configuration, 12, 13);
		            int availability_of_Extension_Line_Category_Information = mid(map_File_Configuration, 11, 12);
		            int tsk_File_Size = (availability_of_Header_Information * Byte_of_Header_Information
                            + availability_of_Test_Result_Information_per_Die * nTotalChips * 6
                            + availability_of_Line_Category_Information * nTotalChips * 8);
		            int map[][][] = new int[(int) (rowsize + 1L)][ (int) (columnsize + 1L)][2];
		            long r_y = 0;
		            for (int p = 1; p <= rowsize; p++)
		            {
		                for (int q = 1; q <= columnsize; q++)
		                {
		                    int startPos = (int) (((test_Die_Information_Address) + (6 * (((p - 1) * ((int)columnsize)) + q - 1))) + 1);
		                    int length = (int) (((test_Die_Information_Address) + (6 * (((p - 1) * ((int)columnsize)) + q - 1))) + 6);
		                    String onedieresult = byteToBinary(readBuff, startPos, length).toString();
		                    int x = Integer.parseInt(onedieresult.substring(8, 17),2);
		                    int y = Integer.parseInt(onedieresult.substring(24, 33), 2);
		                    int category = Integer.parseInt(onedieresult.substring(43, 49), 2);
		                    long testDieVal = Long.parseLong(onedieresult.substring(17, 19), 2);
		                    long passDieVal = Long.parseLong(onedieresult.substring(1, 3), 2);
		                    boolean isTestDie;
		                    boolean isPassDie;

		                    if (testDieVal == 1)
		                    {
		                        isTestDie = true;
		                    }
		                    else
		                    {
		                    	isTestDie = false;
		                    }
		                    if (passDieVal == 1)
		                    {
		                    	isPassDie = true;
		                    }
		                    else
		                    {
		                    	isPassDie = false;
		                    }

		                    if (r_y >= 511 && q == (int)columnsize)
		                    {
		                        r_y++;
		                    }
		                    else if (r_y < 511)
		                    {
		                        r_y = y;
		                    }

		                    if (isTestDie)
		                    {
		                        int m = 0;
		                        int n = 0;
		                        if (refdieY <= 1)
		                        {
		                            n = (int)((x - refdieX)) + 1;
		                           m = (int)((r_y - refdieY)) + 1;
		                        }
		                        else
		                        {
		                            n = x;
		                          m = (int)r_y;
		                        }
		                        map[m][n][(int)(0L)] = (int)category;

		                        if (isPassDie)
		                        {
		                        	map[m][n][(int)(1L)] = 4;
		                        }
		                        else
		                        {
		                        	map[m][n][(int)(1L)] = 3;
		                        }
		                    }
		                }
		            }
		            int passbin2 = 0;
		            int passbin3 = 0;
		            int passbin4 = 0;
		            int num3 = 0;
		            int num4 = 0;
		            int num7 = 2;
		            int num8 = 0;
		            int num9 = 0;
		            for (num3 = 0; num3 < rowsize; num3++)
		            {
		                for (num4 = 0; num4 < columnsize; num4++)
		                {
		                    if ((map[num3][num4][1] != 0L) && (((int)map[num3][num4][0]) == num7))
		                    {
		                        num8++;
		                    }
		                    if ((map[num3][num4][1] != 0L) && (((int)map[num3][num4][0]) == 3))
		                    {
		                        num9++;
		                    }
		                    if ((map[num3][num4][1] != 0L) && (((int)map[num3][num4][0]) == 4))
		                    {
		                      passbin4++;
		                    }
		                }
		            }
		            passbin2 = num8;
		            passbin3 = num9;
		            int tested_die = 0;
		            int pass_die = 0;
		            int fail_die = 0;
		            int gooddie=0;
		            for (int x = 0; x < (int) (rowsize + 1L); x++)
		            {
		                for (int y = 0; y < (int) (columnsize + 1L); y++)
		                {
		                    int category = map[x][y][1];
		                    int binNum = map[x][y][0]; 
		                    switch (category)
		                    {
		                        case 0://not test die
		                            break;

		                        case 4://pass die
		                            tested_die++;
		                            pass_die++;
		                            break;

		                        case 3://fail die
		                            tested_die++;
		                            fail_die++;
		                            break;

		                        default:
		                            break;
		                    }
		                }
		            }
		            tskinfo.setTotal_Dice(tested_die);
		            tskinfo.setTotal_Pass_Dice(pass_die);
		            tskinfo.setTotal_Fail_Dice(fail_die);
		            if (tested_die != 0 && pass_die != 0)
		            {
		            	tskinfo.setTotal_Yield((String.format("%g",(Float.parseFloat(String.valueOf(pass_die)) * 100) / Integer.parseInt(String.valueOf(tested_die)))) + "%");
		            }
		            tskinfo.setPassbin2(String.valueOf(passbin2));
		            tskinfo.setPassbin3(String.valueOf(passbin3));
		            tskinfo.setPassbin4(String.valueOf(passbin4));
		            tskinfo.setWafer_ID(wafer_ID);
		            tskinfo.setLot_No(lot_No);
		            tskinfo.setDevice_Name(device_Name);
		            tskinfo.setOperator_Name(operator_Name);
		            tskinfo.setIndex_X(index_Size_X);
		            tskinfo.setIndex_Y(index_Size_Y);
		            if ((startTime_Year != null && startTime_Year != "") 
		            		&& (startTime_Month != null && startTime_Month != "")
		            		&& (startTime_Day != null && startTime_Day != "")
		            		&& (startTime_Hour != null && startTime_Hour != "")
		            		&& (startTime_Minute != null && startTime_Minute != "")) {
		            	//tskinfo.setStart_Time(startTime_Year + "-" + startTime_Month + "-" 
		            	//	+ startTime_Day + "-" + startTime_Hour + "-" + startTime_Minute);
		            	tskinfo.setStart_Time(String.format("%s-%s-%s %s:%s", 
		            			startTime_Year,startTime_Month,startTime_Day,startTime_Hour,startTime_Minute));
		            }
		            if ((endTime_Year != null && endTime_Year != "") 
		            		&& (endTime_Month != null && endTime_Month != "")
		            		&& (endTime_Day != null && endTime_Day != "")
		            		&& (endTime_Hour != null && endTime_Hour != "")
		            		&& (endTime_Minute != null && endTime_Minute != "")) {
		            	//tskinfo.setEnd_Time(endTime_Year + "-" + endTime_Month + "-" 
			            //		+ endTime_Day + "-" + endTime_Hour + "-" + endTime_Minute);
		            	tskinfo.setEnd_Time(String.format("%s-%s-%s %s:%s", 
		            			endTime_Year,endTime_Month,endTime_Day,endTime_Hour,endTime_Minute));
		            }
		            if ((loadTime_Year != null && loadTime_Year != "") 
		            		&& (loadTime_Month != null && loadTime_Month != "")
		            		&& (loadTime_Day != null && loadTime_Day != "")
		            		&& (loadTime_Hour != null && loadTime_Hour != "")
		            		&& (loadTime_Minute != null && loadTime_Minute != "")) {
		            	//tskinfo.setLoad_Time(loadTime_Year + "-" + loadTime_Month + "-" 
			            //		+ loadTime_Day + "-" + loadTime_Hour + "-" + loadTime_Minute);
		            	tskinfo.setLoad_Time(String.format("%s-%s-%s %s:%s", 
		            			loadTime_Year,loadTime_Month,loadTime_Day,loadTime_Hour,loadTime_Minute));
		            }
		            if ((unLoadTime_Year != null && unLoadTime_Year != "") 
		            		&& (unLoadTime_Month != null && unLoadTime_Month != "")
		            		&& (unLoadTime_Day != null && unLoadTime_Day != "")
		            		&& (unLoadTime_Hour != null && unLoadTime_Hour != "")
		            		&& (unLoadTime_Minute != null && unLoadTime_Minute != "")) {
		            	//tskinfo.setUnLoad_Time(unLoadTime_Year + "-" + unLoadTime_Month + "-" 
			            //		+ unLoadTime_Day + "-" + unLoadTime_Hour + "-" + unLoadTime_Minute);
		            	tskinfo.setUnLoad_Time(String.format("%s-%s-%s %s:%s", 
		            			unLoadTime_Year,unLoadTime_Month,unLoadTime_Day,unLoadTime_Hour,unLoadTime_Minute));
		            }
		            //gooddie = pass_die - Num(ACETEC_VI_FAIL) - Num(CLIENT_VI_FAIL);
		            tskinfo.setGood_Die(gooddie);
		            //this.total_die += tested_die;
		            //this.total_wafer++;
		            //this.total_passdie += pass_die;
		            //this.total_faildie += fail_die;
		           
		            //this.passbin2 += this.mapFile.passbin2;
		            //this.passbin3 += this.mapFile.passbin3;
		            //this.passbin4 += this.mapFile.passbin4;
		            tskinfos.add(tskinfo);
				}
			}
			return InvokeResult.success(tskinfos);
		} catch (SocketException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} finally {  
              if (in != null || fileIn != null) {  
                try {  
                	in.close();
		            fileIn.close(); 
                 } catch (IOException e1) {  
                 	 e1.printStackTrace();
                 	return InvokeResult.failure(e1.getMessage());
                 }  
              }  
        }  
	}
	
	private int getIntBinary(byte[] bts, int begin, int end)
    {
		String str = "0";
        for (int i = begin - 1; i < end; i++)
        {
        	String str2 = byte2bits(bts[i]);
        	int length = 8 - str2.length();
            for (int j = 0 ; j < length ; j++) {
            	str2 = "0" + str2;
            }
            str = str + str2;
        }
        return (int)Long.parseLong(str,2);
    }
	
	private String getByte(byte[] srcArr, int startPos, int endPos)
    {
        String str = new String(srcArr,(startPos-1),(endPos-startPos+1));
        return str.trim();
    }
	
	private String byteToBinary(byte[] srcArr, int startPos, int endPos)
    {
        String str = "0";
        for (int i = startPos - 1; i < endPos; i++)
        {
            	//int z = srcArr[i];
                String str2 = byte2bits(srcArr[i]);
                int length = 8 - str2.length();
                for (int j = 0 ; j < length ; j++) {
                	str2 = "0" + str2;
                }
                str = str + str2;
        }
        return str;
    }
	
	private int mid(String str, int start, int length)
    {
		return Integer.parseInt(str.substring(start, length));
    }
	
	public static String byte2bits(byte b) {

		int z = b;
		z |= 256;
		String str = Integer.toBinaryString(z);
		int len = str.length();
		return str.substring(len - 8, len);
	}

	@Override
	public InvokeResult mapCreate(String upDown, String directoryName,
			String fileNameNum, String mapPath) {
		FileInputStream fileIn = null;
        DataInputStream in = null;
		try {
            File folder = new File(localDirectory + upDown + "/" + directoryName);
            File[] files = folder.listFiles();
			File f = files[Integer.valueOf(fileNameNum)];
			byte[] readBuff = new byte[(int)f.length()];
			fileIn = new FileInputStream(f);
		    in = new DataInputStream(fileIn); 
		    in.read(readBuff);
		    String operator_Name = getByte(readBuff,1,20);  
		    String device_Name = getByte(readBuff,21,36);
		    int Wafer_Size = getIntBinary(readBuff,37,38);
		    int Machine_No = getIntBinary(readBuff,39,40);
		    int index_Size_X = getIntBinary(readBuff,41,44);
		    int index_Size_Y = getIntBinary(readBuff,45,48);
		    int standard_Orientation_Flat_Direction = getIntBinary(readBuff,49,50);
		    int final_Editing_Machine = getIntBinary(readBuff,51,51);
		    int map_Version = getIntBinary(readBuff,52,52);
		    long columnsize = getIntBinary(readBuff,53,54);
		    long rowsize = getIntBinary(readBuff,55,56);
		    int map_Data_Form = getIntBinary(readBuff,57,60);
		    String wafer_ID = getByte(readBuff,61,81);
		    int number_of_Probing = getIntBinary(readBuff,82,82);
		    String lot_No = getByte(readBuff,83,100);
		    int cassette_No = getIntBinary(readBuff,101,102);
		    int slot_No = getIntBinary(readBuff,103,104);
		    int x_axis_coordinates_increase_direction = getIntBinary(readBuff,105,105);
		    int y_axis_coordinates_increase_direction = getIntBinary(readBuff,106,106);
		    int reference_die_setting_procedures = getIntBinary(readBuff,107,107);
		    int reserved_108 = getIntBinary(readBuff,108,108);
		    int target_die_position_X = getIntBinary(readBuff,109,112);
		    int target_die_position_Y = getIntBinary(readBuff,113,116);
		    int refdieX = getIntBinary(readBuff,117,118);
		    if (refdieX > 20)
            {
                refdieX = 1;
            }
		    int refdieY = getIntBinary(readBuff,119,120);
		    int probing_start_position = getIntBinary(readBuff, 121, 121);
		    int probing_direction = getIntBinary(readBuff, 122, 122);
		    int reserved_123_124 = getIntBinary(readBuff, 123, 124);
		    int distance_X_to_wafer_center_die_origin = getIntBinary(readBuff, 125, 128);
		    int distance_Y_to_wafer_center_die_origin = getIntBinary(readBuff, 129, 132);
		    int coordinator_X_of_wafer_center_die = getIntBinary(readBuff, 133, 136);
		    int coordinator_Y_of_wafer_center_die = getIntBinary(readBuff, 137, 140);
		    int first_Die_Coordinator_X = getIntBinary(readBuff, 141, 144);
		    //int first_Die_Coordinator_Y = getIntBinary(readBuff, 145, 148);
		    String startTime_Year = getByte(readBuff, 149, 150);
		    String startTime_Month = getByte(readBuff, 151, 152);
		    String startTime_Day = getByte(readBuff, 153, 154);
		    String startTime_Hour = getByte(readBuff, 155, 156);
		    String startTime_Minute = getByte(readBuff, 157, 158);
		    int startTime_Reserve = getIntBinary(readBuff, 159, 160);
		    String endTime_Year = getByte(readBuff, 161, 162);
		    String endTime_Month = getByte(readBuff, 163, 164);
		    String endTime_Day = getByte(readBuff, 165, 166);
		    String endTime_Hour = getByte(readBuff, 167, 168);
		    String endTime_Minute = getByte(readBuff, 169, 170);
		    int endTime_Reserve = getIntBinary(readBuff, 171, 172);
            String loadTime_Year = getByte(readBuff, 173, 174);
            String loadTime_Month = getByte(readBuff, 175, 176);
            String loadTime_Day = getByte(readBuff, 177, 178);
            String loadTime_Hour = getByte(readBuff, 179, 180);
            String loadTime_Minute = getByte(readBuff, 181, 182);
            int loadTime_Reserve = getIntBinary(readBuff, 183, 184);
            String unLoadTime_Year = getByte(readBuff, 185, 186);
            String unLoadTime_Month = getByte(readBuff, 187, 188);
            String unLoadTime_Day = getByte(readBuff, 189, 190);
            String unLoadTime_Hour = getByte(readBuff, 191, 192);
            String unLoadTime_Minute = getByte(readBuff, 193, 194);
            String start_Time = "";
            String end_Time = "";
            String load_Time = "";
            String unload_Time = "";
            if ((startTime_Year != null && startTime_Year != "") 
            		&& (startTime_Month != null && startTime_Month != "")
            		&& (startTime_Day != null && startTime_Day != "")
            		&& (startTime_Hour != null && startTime_Hour != "")
            		&& (startTime_Minute != null && startTime_Minute != "")) {
            	//start_Time = startTime_Year + "-" + startTime_Month + "-" 
            	//	+ startTime_Day + "-" + startTime_Hour + "-" + startTime_Minute;
            	start_Time = String.format("%s-%s-%s %s:%s", 
            			startTime_Year,startTime_Month,startTime_Day,startTime_Hour,startTime_Minute);
            }
            if ((endTime_Year != null && endTime_Year != "") 
            		&& (endTime_Month != null && endTime_Month != "")
            		&& (endTime_Day != null && endTime_Day != "")
            		&& (endTime_Hour != null && endTime_Hour != "")
            		&& (endTime_Minute != null && endTime_Minute != "")) {
            	//end_Time = endTime_Year + "-" + endTime_Month + "-" 
	            //		+ endTime_Day + "-" + endTime_Hour + "-" + endTime_Minute;
            	end_Time = String.format("%s-%s-%s %s:%s", 
            			endTime_Year,endTime_Month,endTime_Day,endTime_Hour,endTime_Minute);
            }
            if ((loadTime_Year != null && loadTime_Year != "") 
            		&& (loadTime_Month != null && loadTime_Month != "")
            		&& (loadTime_Day != null && loadTime_Day != "")
            		&& (loadTime_Hour != null && loadTime_Hour != "")
            		&& (loadTime_Minute != null && loadTime_Minute != "")) {
            	//load_Time = loadTime_Year + "-" + loadTime_Month + "-" 
	            //		+ loadTime_Day + "-" + loadTime_Hour + "-" + loadTime_Minute;
            	load_Time = String.format("%s-%s-%s %s:%s", 
            			loadTime_Year,loadTime_Month,loadTime_Day,loadTime_Hour,loadTime_Minute);
            }
            if ((unLoadTime_Year != null && unLoadTime_Year != "") 
            		&& (unLoadTime_Month != null && unLoadTime_Month != "")
            		&& (unLoadTime_Day != null && unLoadTime_Day != "")
            		&& (unLoadTime_Hour != null && unLoadTime_Hour != "")
            		&& (unLoadTime_Minute != null && unLoadTime_Minute != "")) {
            	//unload_Time = unLoadTime_Year + "-" + unLoadTime_Month + "-" 
	            //		+ unLoadTime_Day + "-" + unLoadTime_Hour + "-" + unLoadTime_Minute;
            	unload_Time = String.format("%s-%s-%s %s:%s", 
            			unLoadTime_Year,unLoadTime_Month,unLoadTime_Day,unLoadTime_Hour,unLoadTime_Minute);
            }
            int unLoadTime_Reserve = getIntBinary(readBuff, 195, 196);
            int machine_No_1 = getIntBinary(readBuff, 197, 200);
            int machine_No_2 = getIntBinary(readBuff, 201, 204);
            int special_Characters = getIntBinary(readBuff, 205, 208);
            int testing_End_Information = getIntBinary(readBuff, 209, 209);
            int reserve_210 = getIntBinary(readBuff, 210, 210);
            int tested_dice = getIntBinary(readBuff, 211, 212);
            int tested_pass_dice = getIntBinary(readBuff, 213, 214);
            int tested_fail_dice = getIntBinary(readBuff, 215, 216);
            int test_Die_Information_Address = getIntBinary(readBuff, 217, 220);
            int number_of_line_category_data = getIntBinary(readBuff, 221, 224);
            int line_category_address = getIntBinary(readBuff, 225, 228);
            String map_File_Configuration = byteToBinary(readBuff, 229, 230);
            int max_Multi_ = getIntBinary(readBuff, 231, 232);
            int max_Categories = getIntBinary(readBuff, 233, 234);
            int reserve_235236 = getIntBinary(readBuff, 235, 236);
            int nTotalChips = (int)(columnsize * rowsize);
            int Byte_of_Header_Information = 236;
            int Byte_of_Extended_Header_Information = 172;

            // Map_File_Configuration
            int availability_of_Header_Information = mid(map_File_Configuration, 16, 17);
            int availability_of_Test_Result_Information_per_Die = mid(map_File_Configuration, 15, 16);
            int availability_of_Line_Category_Information = mid(map_File_Configuration, 14, 15);
            int availability_of_Extension_Header_Information = mid(map_File_Configuration, 13, 14);
            int availability_of_Test_Result_Information_per_Extension_Die = mid(map_File_Configuration, 12, 13);
            int availability_of_Extension_Line_Category_Information = mid(map_File_Configuration, 11, 12);
            int tsk_File_Size = (availability_of_Header_Information * Byte_of_Header_Information
                   + availability_of_Test_Result_Information_per_Die * nTotalChips * 6
                   + availability_of_Line_Category_Information * nTotalChips * 8);
            long map[][][] = new long[(int) (rowsize + 1L)][(int) (columnsize + 1L)][2];
            long r_y = 0;
            StringBuilder builder = new StringBuilder();
            for (int p = 1; p <= rowsize; p++)
            {
            	builder.append("<tr>\r");
                for (int q = 1; q <= columnsize; q++)
                {
                    int startPos = (int) (((test_Die_Information_Address) + (6 * (((p - 1) * ((int)columnsize)) + q - 1))) + 1);
                    int length = (int) (((test_Die_Information_Address) + (6 * (((p - 1) * ((int)columnsize)) + q - 1))) + 6);
                    String onedieresult = byteToBinary(readBuff, startPos, length).toString();
                    int x = Integer.parseInt(onedieresult.substring(8, 17),2);
                    int y = Integer.parseInt(onedieresult.substring(24, 33), 2);
                    int category = Integer.parseInt(onedieresult.substring(43, 49), 2);
                    long testDieVal = Long.parseLong(onedieresult.substring(17, 19), 2);
                    long passDieVal = Long.parseLong(onedieresult.substring(1, 3), 2);
                    long siteNo = Long.parseLong(onedieresult.substring(35, 41), 2);
                    boolean isTestDie;
                    boolean isPassDie;

                    if (testDieVal == 1)
                    {
                        isTestDie = true;
                    }
                    else
                    {
                    	isTestDie = false;
                    }
                    if (passDieVal == 1)
                    {
                    	isPassDie = true;
                    }
                    else
                    {
                    	isPassDie = false;
                    }

                    if (r_y >= 511 && q == (int)columnsize)
                    {
                        r_y++;
                    }
                    else if (r_y < 511)
                    {
                        r_y = y;
                    }

                    if (isTestDie)
                    {
                        int m = 0;
                        int n = 0;
                        if (refdieY <= 1)
                        {
                            n = (int)((x - refdieX)) + 1;
                           m = (int)((r_y - refdieY)) + 1;
                        }
                        else
                        {
                            n = x;
                          m = (int)r_y;
                        }
                        map[m][n][(int)(0L)] = category;

                        if (isPassDie)
                        {
                        	map[m][n][(int)(1L)] = 4;
                        	builder.append(String.format("<td class=\"b\" title=\"%d [Site]%s  [PASS]  (X:%s Y:%s)\">%d",map[m][n][0], String.valueOf(siteNo), String.valueOf(m), String.valueOf(n), map[m][n][0]));
                        }
                        else
                        {
                        	map[m][n][(int)(1L)] = 3;
                        	builder.append(String.format("<td class=\"r\" title=\"%d [Site]%s  [FAIL]  (X:%s Y:%s)\">%d",map[m][n][0], String.valueOf(siteNo), String.valueOf(m), String.valueOf(n), map[m][n][0]));
                        }
                    } else {
                    	builder.append("<td>&nbsp;");
                    }
                    builder.append("</td>\r");
                }
                builder.append("</tr>\r");
            }
            int tested_die = 0;
            int pass_die = 0;
            int fail_die = 0;
            for (int i = 0; i < (int) (rowsize + 1L); i++)
            {
                for (int j = 0; j < (int) (columnsize + 1L); j++)
                {
                    int category = (int)map[i][j][1];
                    switch (category)
                    {
                        case 0://not test die
                            break;

                        case 4://pass die
                            tested_die++;
                            pass_die++;
                            break;

                        case 3://fail die
                            tested_die++;
                            fail_die++;
                            break;

                        default:
                            break;
                    }
                }
            }
            String yield = "";
            if (tested_die != 0 && pass_die != 0)
            {
                yield = String.format("%g",(Float.parseFloat(String.valueOf(pass_die)) * 100) / Integer.parseInt(String.valueOf(tested_die))) + "%";
            }

            String str2 = "";
            FileInputStream reader = new FileInputStream(mapPath + "/" + "map.html");
            InputStreamReader isr = new InputStreamReader(reader, "UTF-8");  
            BufferedReader reader2 = new BufferedReader(isr);  
            //BufferedReader  reader2 = new BufferedReader(reader,"UTF-8");
            String str3 = "";
            while ((str3 = reader2.readLine()) != null)
            {
                str2 = str2 + str3 + "\r";
            }

            str2 = str2.replace("{%body%}", builder.toString());
            str2 = str2.replace("{%deviceName%}", device_Name);
            str2 = str2.replace("{%indexX%}", String.valueOf(index_Size_X));
            str2 = str2.replace("{%indexY%}", String.valueOf(index_Size_Y));
            str2 = str2.replace("{%operatorName%}", operator_Name);
            str2 = str2.replace("{%waferID%}", wafer_ID);
            str2 = str2.replace("{%lotNO%}", lot_No);
            str2 = str2.replace("{%waferTestStartTime%}", start_Time);
            str2 = str2.replace("{%waferTestEndTime%}", end_Time);
            str2 = str2.replace("{%waferLoadTime%}", load_Time);
            str2 = str2.replace("{%waferUnloadTime%}", unload_Time);
            str2 = str2.replace("{%totalDice%}", String.valueOf(tested_die));
            str2 = str2.replace("{%totalPassDice%}", String.valueOf(pass_die));
            str2 = str2.replace("{%totalFailDice%}", String.valueOf(fail_die));
            str2 = str2.replace("{%totalYield%}", yield);
            
            ArrayList list = new ArrayList();
            int num3 = 0;
            int num4 = 0;
            int num5 = 0;
            int num6 = 0;
            int num7 = 0;
            int num8 = 0;
            while (num3 < rowsize)
            {
                num4 = 0;
                while (num4 < columnsize)
                {
                    boolean flag = true;
                    if (map[num3][num4][1] != 0L)
                    {
                        for (int i = 0; i < list.size(); i++)
                        {
                        	num7 = (int)list.get(i);
                            if (((int)map[num3][num4][0]) == num7)
                            {
                                flag = false;
                            }
                        }
                        if (flag)
                        {
                            list.add((int)map[num3][num4][0]);
                        }
                    }
                    num4++;
                }
                num3++;
            }
            //list.add(e);
            Collections.sort(list);
            StringBuilder builder2 = new StringBuilder();
            for (int j = 0 ; j < list.size() ; j++)
            {
            	num7 = (int)list.get(j);
                num8 = 0;
                for (num3 = 0; num3 < rowsize; num3++)
                {
                    for (num4 = 0; num4 < columnsize; num4++)
                    {
                        if ((map[num3][num4][1] != 0L) && (((int)map[num3][num4][0]) == num7))
                        {
                            num8++;
                        }
                    }
                }
                double percent = (((double)num8) / ((double)tested_dice)) * 100.0;
                DecimalFormat df = new DecimalFormat("#0.00");   
                String num9 = df.format(percent);
                builder2.append(String.format("<tr height='15px'><td>%s</td><td>%s</td><td>%s</td></tr>", String.valueOf(num7), String.valueOf(num8), 
                    		num9+"%"));
            }
            str2 = str2.replace("{%binData%}", builder2.toString());
			return InvokeResult.success(str2);
		} catch (SocketException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} finally {  
              if (in != null || fileIn != null) {  
                try {  
                	in.close();
		            fileIn.close(); 
                 } catch (IOException e1) {  
                 	 e1.printStackTrace();
                 	return InvokeResult.failure(e1.getMessage());
                 }  
              }  
        }  
	}

	@Override
	public InvokeResult resolve3360File(String customer, String testType, String device, String lotID) {
		FTPClient ftpClient = new FTPClient();
        String directory;
        try {
			ftpClient.connect(sumryServer);
			ftpClient.login(sumryFtpUserName, sumryFtpUserPwd);
			ftpClient.setDefaultTimeout(15000);
			ftpClient.setSoTimeout(30000);
			ftpClient.setDataTimeout(15000);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
			    throw new RuntimeException("连接FTP服务器失败!");
			}
			if(ftpClient.changeWorkingDirectory(customer + "/" + testType + "/" + device + "/" + lotID)) {
				directory = customer + "/" + testType + "/" + device + "/" + lotID;
			} else {
				ftpClient.changeWorkingDirectory("/");
				ftpClient.changeWorkingDirectory(customer + "/" + testType + "/" + device + "/" + device + "/" + lotID);
				directory = customer + "/" + testType + "/" + device + "/" + device + "/" + lotID;
			}
	        FTPFile[] ftpFiles = ftpClient.listFiles();
	        List<TestData3360InfoDTO> infoLists3360 = new ArrayList<TestData3360InfoDTO>();
	        File folder = new File(localDirectory + "/" + "File3360" + "/" + directory);
			if (!folder.exists()) {
				folder.mkdirs();
			}
		     for (int i = 0; i < ftpFiles.length ; i++) {
		    	 if (ftpFiles[i].getName().endsWith(".txt")) {
		    		 if (ftpFiles[i].getName().contains("Amazon") && ftpFiles[i].getName().contains("sumry"))
	                 {
		    			 
					    ftpClient.retrieveFile( new String(ftpFiles[i].getName().getBytes(), "iso-8859-1"), 
						      new DataOutputStream(new FileOutputStream(localDirectory + "/" + "File3360" + "/" + directory + "/" +ftpFiles[i].getName())));
		    			TestData3360InfoDTO testData3360Info = new TestData3360InfoDTO();
		    			testData3360Info = loadingData_Amazon(ftpFiles[i].getName(),directory);
		    			infoLists3360.add(testData3360Info);
	                 }
	                 else if (ftpFiles[i].getName().contains("sumry"))
	                 {
	                	 ftpClient.retrieveFile( new String(ftpFiles[i].getName().getBytes(), "iso-8859-1"), 
						 	      new DataOutputStream(new FileOutputStream(localDirectory + "/" + "File3360" + "/" + directory + "/" +ftpFiles[i].getName())));
	                	 TestData3360InfoDTO testData3360Info = new TestData3360InfoDTO();
			    		 testData3360Info = loadingData_lu(ftpFiles[i].getName(),directory);
			    		 infoLists3360.add(testData3360Info);
	                 }
			     }
		     }
		     if (infoLists3360.size() != 0) {
		    	 if (infoLists3360.get(0).getDbin() != null &&
				    	 infoLists3360.get(0).getMap() == null) {
				    	 int size = 0;
				    	 for (int j = 0 ; j < infoLists3360.size() ; j++) {
					    	 if ( size < infoLists3360.get(j).getMapSize()) {
					    		 size = infoLists3360.get(j).getMapSize();
					    	 }
					     }
					     for (int p = 0; p < infoLists3360.size(); p++)
				         {
					    	 int startNum = infoLists3360.get(p).getMapSize();
					    	 Map<String, Object> newmap = new HashMap<>();
					    	 newmap = infoLists3360.get(p).getDbin();
					    	 String  columnname = "DBIN000";
					    	 if ( startNum < size ) {
					    		 for (int q = startNum + 1 ; q <= size ; q++) {
					    			 if ( String.valueOf(q).length() < String.valueOf(size).length() ) {
					                 	 columnname = "DBIN" + StringUtils.leftPad(String.valueOf(q),String.valueOf(q).length()+(String.valueOf(size).length()-String.valueOf(q).length()),"0");
					                 } else {
					                     columnname = "DBIN" + String.valueOf(q);
					                 }
					    			 newmap.put(columnname, "0");
					    		 }
					    	 }  
					    	 infoLists3360.get(p).setDbin(newmap);
				         }
				     }
		     } else {
		    	 throw new RuntimeException("sumry文件不存在！");
		     }
			return InvokeResult.success(infoLists3360);
		} catch (SocketException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}
	
	private TestData3360InfoDTO loadingData_Amazon(String fileName,String directory)
    {
        try
        {
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	TestData3360InfoDTO testData3360Info = new TestData3360InfoDTO();
        	Map<String, Object> map = new HashMap<>();
            int num = 0;
            File file = new File(directory + "/" + fileName);
            FileInputStream stream = new FileInputStream(file);
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "gb2312");
            char[] readBuff = new char[(int)file.length()];
            read.read(readBuff);
            String input = String.valueOf(readBuff);
            
            String[] strArray = input.split("\n\\s*\r");
            testData3360Info.setWaferSummaryData(strArray[0].split("]")[1].replace(" ", "").replace("\r", "").replace("\n", ""));
            for (int p = 0 ; p < strArray.length ; p++)
            {
                String[] strArray2 = strArray[p].split("\r|\n");
                if ("".equals(strArray2[0]) || " ".equals(strArray2[0])) {
            		for ( int i = 0; i < strArray2.length-1; i++) {
            			strArray2[i] = strArray2[i+1];
            		}
            	}
                StringBuffer sb = new StringBuffer();
                for ( int i = 0; i < strArray2.length; i++) {
        			if ("".equals(strArray2[i]) || " ".equals(strArray2[i])){
        				strArray2[i] = ";";
        			}
        			sb.append(strArray2[i]);
        		}
                strArray2 = sb.toString().split(";|;;");
                String startTime = "";
                String endTime = "";
                if (strArray2.length > 1)
                {
                    int num2;
                    String[] strArray6;
                    if ( "[HEADER]".equals(strArray2[0]) )
                    {
                        for (int i = 1; i < strArray2.length; i++)
                        {
                            String str = strArray2[i];
                            String[] infos = str.split(":" , -1);
                            if (infos[1].indexOf("(") != -1)
                            {
                                infos[1] = infos[1].split("[(]")[0];
                            }
                            if ( "Device Name".equals(infos[0].trim()) )
                            {
                            	testData3360Info.setDevice(infos[1].trim());
                            }
                            if ("Test Program Name".equals(infos[0].trim())
                                && "".equals(infos[2].trim()))
                            {
                                String fileNameWithoutExtension = infos[2].trim().replaceAll("[.][^.]+$", "");// 没有扩展名的文件名
                                testData3360Info.setProgramName(fileNameWithoutExtension);
                            }
                            if ( "Handler/Prober Name".equals(infos[0].trim()) )
                            {
                            	testData3360Info.setTesterNumber(infos[1].trim());
                                if (infos.length > 2)
                                {
                                    String strTemperature = infos[2].trim().split(" ")[0];
                                    float ftemperature = 0f;
                                    ftemperature = Float.parseFloat(strTemperature);
                                    int inttemperature = (int)(ftemperature + 0.5);
                                    testData3360Info.setTemperature(String.valueOf(inttemperature).trim());

                                }
                            }
                            if ( "Probe Card No".equals(infos[0].trim()) )
                            {
                            	testData3360Info.setProberCard(infos[1].trim());
                            }
                            if ( "Lot ID".equals(infos[0].trim()) )
                            {
                            	testData3360Info.setLotID(infos[1].trim());
                            }
                            if ( "Wafer ID".equals(infos[0].trim()) )
                            {
                            	testData3360Info.setwID(infos[1].trim());
                            }
                            if ( "Tester ID/Tester Station".equals(infos[0].trim()) )
                            {
                            	testData3360Info.setTestStep(infos[1].replace("/", " ").trim());
                            }
                            if ( "OPERATOR".equals(infos[0].trim()) )
                            {
                            	testData3360Info.setOperator(infos[1].trim());
                            }
                            if ( "Test Start Date".equals(infos[0].trim()) )
                            {
                                startTime = infos[1].trim().split(" ")[3] + ":" + infos[2].trim() + ":" 
                                          + infos[3].trim() + " " + infos[1].trim().split(" ")[0] + " " 
                                		  + infos[1].trim().split(" ")[1] + " " + infos[1].trim().split(" ")[2];
                                Date date = null ;
                                String dsd = df.format(date.parse(startTime));
                                testData3360Info.setStartDate(dsd.split(" ")[0].trim());
                                testData3360Info.setStartTime(dsd.split(" ")[1].trim());
                            }
                            if ( "Test End Date".equals(infos[0].trim()) )
                            {
                                endTime = infos[1].trim().split(" ")[3] + ":" + infos[2].trim() + ":"
                                        + infos[3].trim() + " " + infos[1].trim().split(" ")[0] + " " 
                                		+ infos[1].trim().split(" ")[1] + " " + infos[1].trim().split(" ")[2];
                                Date date = null ;
                                String dsd = df.format(date.parse(endTime));
                                testData3360Info.setEndDate(dsd.split(" ")[0].trim());
                                testData3360Info.setEndTime(dsd.split(" ")[1].trim());
                            }
                        }
                        Date date2 = null;
                        long time = date2.parse(endTime) - date2.parse(startTime);
                        long day=time/(24*60*60*1000);
                        long hour=(time/(60*60*1000)-day*24);
                        long min=((time/(60*1000))-day*24*60-hour*60);
                        long s=(time/1000-day*24*60*60-hour*60*60-min*60);
                        String temps = String.valueOf(s);
                        String tempmin = String.valueOf(min);
                        String temphour = String.valueOf(time/(60*60*1000));
                        if ( String.valueOf(s).length() < 2) {
                            temps = StringUtils.leftPad(String.valueOf(s),String.valueOf(s).length()+(2-String.valueOf(s).length()),"0");
                        }
                        if ( String.valueOf(min).length() < 2) {
                        	tempmin = StringUtils.leftPad(String.valueOf(min),String.valueOf(min).length()+(2-String.valueOf(min).length()),"0");
                        }
                        if ( String.valueOf(time/(60*60*1000)).length() < 2) {
                        	temphour = StringUtils.leftPad(String.valueOf(((time/(60*60*1000)))),String.valueOf(((time/(60*60*1000)))).length()+(2-String.valueOf(time/(60*60*1000)).length()),"0");
                        } 
                        String timestr = temphour + ":" 
                                       + temps + ":" 
                        		       + tempmin + "";
                        testData3360Info.setTotalTestTime(timestr.trim());
                        testData3360Info.setRevision("1");
                    }
                    if ( "[SUBHEADER]".equals(strArray2[0]) )
                    {
                        String yieldSumLine = strArray2[2].trim();
                        String[] infos = yieldSumLine.split("\\s{1,}");
                        testData3360Info.setGrossDie(infos[0].trim());
                        testData3360Info.setGoodDie(infos[1].trim());
                        testData3360Info.setYield(infos[2].trim());
                        testData3360Info.setBadDie(infos[3].trim());
                    }
                    if ( "[SOFTWARE BIN]".equals(strArray2[0]) )
                    {
                        int ii = 1;
                        int iiLength = 0;
                        iiLength = Integer.parseInt(strArray2[strArray2.length - 1].toString().split("[(]")[1].split("[)]")[0].replace('!', ' ').trim());
                        List lstNo = new ArrayList();
                        List<String> lstCount = new ArrayList<String>();
                        for (int i = 2; i < strArray2.length; i++)
                        {
                            String yieldSumLine = strArray2[i].trim();
                            String[] infos = yieldSumLine.split("\\s{1,}");
                            int intNo = 0;
                            String strNo = infos[0].toString().split("[(]")[1].split("[)]")[0].replace('!', ' ').trim();
                            intNo = Integer.parseInt(strNo);
                            if (!lstNo.contains(intNo))
                            {
                                lstNo.add(intNo);
                                lstCount.add(infos[1].trim());
                            }
                            else
                            {
                                int intindex = lstNo.indexOf(intNo);
                                int douCount = Integer.parseInt(lstCount.get(intindex)) + Integer.parseInt(infos[1].trim());
                                lstCount.set(intindex, String.valueOf(douCount));
                            }
                        }
                        for (int i = 1; i <= iiLength; i++)
                        {
                            int intSort = lstNo.indexOf(i);
                            String  columnname = "DBIN000";
                            if ( String.valueOf(i).length() < String.valueOf(iiLength).length() ) {
                            	columnname = "DBIN" + StringUtils.leftPad(String.valueOf(i),String.valueOf(i).length()+(String.valueOf(iiLength).length()-String.valueOf(i).length()),"0");
                            } else {
                                columnname = "DBIN" + String.valueOf(i);
                            }
                            if (intSort != -1)
                            {
                                map.put(columnname, lstCount.get(intSort).trim());
                            }
                            else
                            {
                            	map.put(columnname, "0");
                            }
                        }
                        testData3360Info.setMapSize(map.size());
                        testData3360Info.setDbin(map);
                    }
                }
            }
            return testData3360Info;
        }
        catch (Exception ex)
        {
        	throw new RuntimeException("文件" + fileName + "格式有错" + ex);
        }
    }
	
	/**
	 * 加载3360数据信息(new)lu change
	 * @author HongYu
	 * @version 2016/6/7
	 * @return 
	 */
    private TestData3360InfoDTO loadingData_lu(String fileName , String directory)
    {
        try
        {
        	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	TestData3360InfoDTO testData3360Info = new TestData3360InfoDTO();
        	Map<String, Object> map = new HashMap<>();
        	LinkedList listTitle = new LinkedList();
        	LinkedList listValue = new LinkedList();
            int num = 0;
            File file = new File(localDirectory + "/" + "File3360" + "/" + directory + "/" + fileName);
            FileInputStream stream = new FileInputStream(file);
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), "gb2312");
            //byte[] readBuff = new byte[(int)file.length()];
            char[] readBuff = new char[(int)file.length()];
            read.read(readBuff);
            String input = String.valueOf(readBuff);
            String[] strArray = input.split("\n\\s*\r");
            
            testData3360Info.setWaferSummaryData(strArray[0].split("]")[1].replace(" ", "").replace("\r", "").replace("\n", ""));
            for (int p = 0 ; p < strArray.length ; p++)
            {
                String[] strArray2 = strArray[p].split("\r|\n");
                if ("".equals(strArray2[0]) || " ".equals(strArray2[0])) {
            		for ( int i = 0; i < strArray2.length-1; i++) {
            			strArray2[i] = strArray2[i+1];
            		}
            	}
                StringBuffer sb = new StringBuffer();
                for ( int i = 0; i < strArray2.length; i++) {
        			if ("".equals(strArray2[i]) || " ".equals(strArray2[i])){
        				strArray2[i] = ";";
        			}
        			sb.append(strArray2[i]);
        		}
                strArray2 = sb.toString().split(";|;;");
                String startTime = "";
                String endTime = "";
                if (strArray2.length > 1)
                {
                    int num2;
                    String[] strArray6;
                    if ("[HEADER]".equals(strArray2[0]))
                    {
                        for (int i = 1; i < strArray2.length; i++)
                        {
                            String str = strArray2[i];
                            String[] infos = str.split(":" , -1);
                            if (infos[1].indexOf("(") != -1)
                            {
                                infos[1] = infos[1].split("[(]")[0];
                            }
                            if (infos.length >= 1 && infos[1] != "")
                            {
                            	map.put(infos[0].trim(), infos[1].trim());
                            }
                            if ( "Test Start Date".equals(infos[0].trim()))
                            {
                                startTime = infos[1].trim().split(" ")[3] + ":" 
                                          + infos[2].trim() + ":" 
                                		  + infos[3].trim() + " " 
                                          + infos[1].trim().split(" ")[0] + " " 
                                		  + infos[1].trim().split(" ")[1] + " " 
                                          + infos[1].trim().split(" ")[2];
                                Date date = null ;
                                String dsd = df.format(date.parse(startTime));
                                testData3360Info.setStartDate(dsd.split(" ")[0].trim());
                                testData3360Info.setStartTime(dsd.split(" ")[1].trim());
                            }
                            if ("Test End Date".equals(infos[0].trim()))
                            {
                                endTime = infos[1].trim().split(" ")[3] + ":" 
                                        + infos[2].trim() + ":" 
                                		+ infos[3].trim() + " " 
                                        + infos[1].trim().split(" ")[0] + " " 
                                		+ infos[1].trim().split(" ")[1] + " " 
                                        + infos[1].trim().split(" ")[2];
                                Date date = null ;
                                String dsd = df.format(date.parse(endTime));
                                testData3360Info.setEndDate(dsd.split(" ")[0].trim());
                                testData3360Info.setEndTime(dsd.split(" ")[1].trim());
                            }
                        }
                        
                        Date date2 = null;
                        long time = date2.parse(endTime) - date2.parse(startTime);
                        long day=time/(24*60*60*1000);
                        long hour=(time/(60*60*1000)-day*24);
                        long min=((time/(60*1000))-day*24*60-hour*60);
                        long s=(time/1000-day*24*60*60-hour*60*60-min*60);
                        String timestr = day + "天" + hour + "小时" + min + "分" + s + "秒";
                        testData3360Info.setTestingDate(timestr);
                    }
                    if ( "[SUBHEADER]".equals(strArray2[0]))
                    {
                        String yieldSumLine = strArray2[2].trim();
                        String[] infos = yieldSumLine.split("\\s{1,}");

                        testData3360Info.setSample(infos[0]);
                        testData3360Info.setPass(infos[1]);
                        testData3360Info.setYield(infos[2]);
                        testData3360Info.setFail(infos[3]);
                        testData3360Info.setFailPercent(infos[4]);
                    }
                    if ("[SOFTWARE BIN]".equals(strArray2[0]))
                    {
                        String yieldSumLine = strArray2[3];
                        String[] infos = yieldSumLine.split(" ");
                        testData3360Info.setSwPassClass2(infos[1]);
                    }
                    if ("[TEST ITEM]".equals(strArray2[0]))
                    {
                        for (num2 = 2; num2 < strArray2.length; num2++)
                        {
                            String yieldSumLine = strArray2[num2].trim();
                            String[] infos = yieldSumLine.split("\\s{1,}");
                            map.put(infos[0].trim()+"-TestItem", infos[1].trim());
                            listTitle.addLast(infos[0].trim()+"-TestItem");
                            listValue.addLast(infos[1].trim());
                            map.put("listTitle", listTitle);
                            map.put("listValue", listValue);
                        }
                    }
                }
                testData3360Info.setMap(map);
            }
            return testData3360Info;
        }
        catch (Exception ex)
        {
        	throw new RuntimeException("文件" + fileName + "格式有错" + ex);
        }
    }
    
    @Override
	public InvokeResult getHYNFileNames(String upDown,String directory) {
		 FTPClient ftpClient = new FTPClient();
		 List fileLists = new ArrayList();
		 List timeLists = new ArrayList();
		 Map<String, Object> map = new HashMap<>();
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 try {
			ftpClient.connect(server);
			ftpClient.login(ftpUserName, ftpUserPwd);
			ftpClient.setDefaultTimeout(15000);
			ftpClient.setSoTimeout(30000);
			ftpClient.setDataTimeout(15000);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
			    throw new RuntimeException("连接FTP服务器失败!");
			}
            ftpClient.changeWorkingDirectory("/" + "map"+ "/" + upDown);
            FTPFile[] ftpFiles = ftpClient.listFiles();
			for (int i = 0 ; i < ftpFiles.length ; i++) {
				String[] directoryName = new String[ftpFiles.length];
				FTPFile file = ftpFiles[i];
				if (file.isDirectory() ) {
					if (directory == null || directory == "") {
						if (!file.getName().contains("..") && !file.getName().contains(".")) {
							fileLists.add(file.getName());
							Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							cal.setTime((Date)(file.getTimestamp().getTime()));
							timeLists.add(df.format(cal.getTime()));
						}
					} else {
						if (!file.getName().contains("..") && !file.getName().contains(".") 
								&& file.getName().contains(directory)) {
							fileLists.add(file.getName());
							Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							cal.setTime((Date)(file.getTimestamp().getTime()));
							timeLists.add(df.format(cal.getTime()));
						}
					}
				}
			}
			map.put("directoryName", fileLists);
			map.put("timeStamp", timeLists);
			return InvokeResult.success(map);
		} catch (SocketException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}
    
    @Override
	public void tskExportExcel(List<TSKInfoDTO> tskInfoDTOs, String fileName) {
        // 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("TSK信息情报");
        sheet.setDefaultColumnWidth(16);
        sheet.setDefaultRowHeightInPoints(20);
        // 第三步，在sheet中添加表头第0行t
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);

        HSSFCell  cell = row.createCell(0);
        cell.setCellValue("Device Name");
        cell.setCellStyle(style);
        cell = row.createCell(1);
        cell.setCellValue("Index X");
        cell.setCellStyle(style);
        cell = row.createCell(2);
        cell.setCellValue("Index Y");
        cell.setCellStyle(style);
        cell = row.createCell(3);
        cell.setCellValue("Operator Name");
        cell.setCellStyle(style);
        cell = row.createCell(4);
        cell.setCellValue("Wafer ID");
        cell.setCellStyle(style);
        cell = row.createCell(5);
        cell.setCellValue("Lot No");
        cell.setCellStyle(style);
        cell = row.createCell(6);
        cell.setCellValue("Start Time");
        cell.setCellStyle(style);
        cell = row.createCell(7);
        cell.setCellValue("End Time");
        cell.setCellStyle(style);
        cell = row.createCell(8);
        cell.setCellValue("Load Time");
        cell.setCellStyle(style);
        cell = row.createCell(9);
        cell.setCellValue("Unload Time");
        cell.setCellStyle(style);
        cell = row.createCell(10);
        cell.setCellValue("Total Dice");
        cell.setCellStyle(style);
        cell = row.createCell(11);
        cell.setCellValue("Total Pass Dice");
        cell.setCellStyle(style);
        cell = row.createCell(12);
        cell.setCellValue("Total Fail Dice");
        cell.setCellStyle(style);
        cell = row.createCell(13);
        cell.setCellValue("Total Yield");
        cell.setCellStyle(style);
        cell = row.createCell(14);
        cell.setCellValue("pass bin2");
        cell.setCellStyle(style);
        cell = row.createCell(15);
        cell.setCellValue("pass bin3");
        cell.setCellStyle(style);
        cell = row.createCell(16);
        cell.setCellValue("pass bin4");
        cell.setCellStyle(style);
        cell = row.createCell(17);
        cell.setCellValue("内部补偿");
        cell.setCellStyle(style);
        cell = row.createCell(18);
        cell.setCellValue("外部补偿");
        cell.setCellStyle(style);
        cell = row.createCell(19);
        cell.setCellValue("Good Die");
        cell.setCellStyle(style);
        cell = row.createCell(20);
        cell.setCellValue("Pass Dice结果");
        cell.setCellStyle(style);
        cell = row.createCell(21);
        cell.setCellValue("内部补偿结果");
        cell.setCellStyle(style);
        cell = row.createCell(22);
        cell.setCellValue("外部补偿结果");
        cell.setCellStyle(style);
        cell = row.createCell(23);
        cell.setCellValue("最终Pass Dice结果");
        cell.setCellStyle(style);
        cell = row.createCell(24);
        cell.setCellValue("客户Pass Dice");
        cell.setCellStyle(style);
        cell = row.createCell(25);
        cell.setCellValue("客户内部补偿");
        cell.setCellStyle(style);
        cell = row.createCell(26);
        cell.setCellValue("客户外部补偿");
        cell.setCellStyle(style);
        cell = row.createCell(27);
        cell.setCellValue("客户最终Pass Dice");
        cell.setCellStyle(style);

	     // 第五步，写入实体数据 实际应用中这些数据从数据库得到，   
	     for (int i = 0; i < tskInfoDTOs.size(); i++)  
	     {  
	    	 TSKInfoDTO tskInfoDTO = (TSKInfoDTO) tskInfoDTOs.get(i);
	         row = sheet.createRow((int) i + 1);   
	         HSSFCell datacell = row.createCell(0);
	         datacell.setCellValue(tskInfoDTO.getDevice_Name());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(1);
	         datacell.setCellValue(tskInfoDTO.getIndex_X()); 
	         datacell.setCellStyle(style);
	         datacell = row.createCell(2);
	         datacell.setCellValue(tskInfoDTO.getIndex_Y()); 
	         datacell.setCellStyle(style);
	         datacell = row.createCell(3);
             datacell.setCellValue(tskInfoDTO.getOperator_Name());
             datacell.setCellStyle(style);
	         datacell = row.createCell(4);
	         datacell.setCellValue(tskInfoDTO.getWafer_ID());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(5);
	         datacell.setCellValue(tskInfoDTO.getLot_No()); 
	         datacell.setCellStyle(style);
	         datacell = row.createCell(6);
	         datacell.setCellValue(tskInfoDTO.getStart_Time());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(7);
	         datacell.setCellValue(tskInfoDTO.getEnd_Time());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(8);
	         datacell.setCellValue(tskInfoDTO.getLoad_Time());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(9);
             datacell.setCellValue(tskInfoDTO.getUnLoad_Time());
             datacell.setCellStyle(style);
	         datacell = row.createCell(10);
	         datacell.setCellValue(tskInfoDTO.getTotal_Dice());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(11);
	         datacell.setCellValue(tskInfoDTO.getTotal_Pass_Dice());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(12);
	         datacell.setCellValue(tskInfoDTO.getTotal_Fail_Dice());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(13);
	         datacell.setCellValue(tskInfoDTO.getTotal_Yield());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(14);
	         datacell.setCellValue(tskInfoDTO.getPassbin2());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(15);
	         datacell.setCellValue(tskInfoDTO.getPassbin3());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(16);
	         datacell.setCellValue(tskInfoDTO.getPassbin4());
	         datacell.setCellStyle(style);
	         datacell = row.createCell(17);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	         datacell = row.createCell(18);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	         datacell = row.createCell(19);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	         datacell = row.createCell(20);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	         datacell = row.createCell(21);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	         datacell = row.createCell(22);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	         datacell = row.createCell(23);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	         datacell = row.createCell(24);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	         datacell = row.createCell(25);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	         datacell = row.createCell(26);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	         datacell = row.createCell(27);
	         datacell.setCellValue("");
	         datacell.setCellStyle(style);
	     }  
	     // 第六步，将文件存到指定位置   
	     try  
	     {  
	 		 //获取类文件所在的路径，为获取web应用路径做准备
	 		 String classPath = this.getClass().getClassLoader().getResource("").getPath();
	 		 //获取模板路径与导出文件的路径
	 		 String templatePath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/";
	 		 String exportPath = templatePath + "export/tsk/";
	 		 File folder = new File(exportPath);
	 		 if (!folder.exists()) {
				folder.mkdirs();
			 }
	         FileOutputStream fout = new FileOutputStream(exportPath + fileName);  
	         wb.write(fout);
	         fout.close();
	     }  
	     catch (IOException e)  
	     {  
	         e.printStackTrace();  
	     }  
	}
    
    @Override
	public String exportHYNCP1Excel(String upDown,String directoryName,List<TSKInfoDTO> tskInfoDTOs) {
    	 if ( !directoryName.endsWith("-CP1") ) {
    		 throw new RuntimeException("导出必须选择左侧列表中以-CP1结尾的Lot");
    	 }
    	 String[] lotIdAndCPN = directoryName.split("-");
    	 String lotId = lotIdAndCPN[0];
    	 String CPX = lotIdAndCPN[1];
    	 File folder = new File(localDirectory + upDown + "/" + directoryName);
         File[] files = folder.listFiles();
    	 String filename = export(lotId,files,CPX,files.length);
		 return filename;
	}
    
    @Override
   	public String exportHYNCP2Excel(String upDown,String directoryName,List<TSKInfoDTO> tskInfoDTOs) {
       	 if ( !directoryName.endsWith("-CP2") ) {
       		 throw new RuntimeException("导出必须选择左侧列表中以-CP2结尾的Lot");
       	 }
       	 String[] lotIdAndCPN = directoryName.split("-");
       	 String lotId = lotIdAndCPN[0];
       	 String CPX = lotIdAndCPN[1];
       	 File folder = new File(localDirectory + upDown + "/" + directoryName);
            File[] files = folder.listFiles();
       	 String filename = export(lotId,files,CPX,files.length);
   		 return filename;
   	}
    
    @Override
	public String exportHYNCP4Excel(String upDown,String directoryName,List<TSKInfoDTO> tskInfoDTOs) {
    	 String fileName = "";
    	 if ( !directoryName.endsWith("-CP1") ) {
    		 throw new RuntimeException("导出必须选择左侧列表中以-CP1结尾的Lot");
    	 }
    	 List CP1MapsPaths = new ArrayList();
         List CP2MapsPaths = new ArrayList();
    	 String[] lotIdAndCPN = directoryName.split("-");
    	 String lotId = lotIdAndCPN[0];
    	 String CPX = lotIdAndCPN[1];
    	 File cp1Folder = new File(localDirectory + upDown + "/" + directoryName);
         File[] cp1files = cp1Folder.listFiles();
         for (int p = 0 ; p < cp1files.length ; p++) {
        	 CP1MapsPaths.add(cp1files[p].getPath());
         }
         FTPClient ftpClient = new FTPClient();
         String directory;
         try {
 			ftpClient.connect(sumryServer);
 			ftpClient.login(sumryFtpUserName, sumryFtpUserPwd);
 			ftpClient.setDefaultTimeout(15000);
 			ftpClient.setSoTimeout(30000);
 			ftpClient.setDataTimeout(15000);
 			int reply = ftpClient.getReplyCode();
 			if (!FTPReply.isPositiveCompletion(reply)) {
 			    throw new RuntimeException("连接FTP服务器失败!");
 			}
 			ftpClient.changeWorkingDirectory("/" + "map"+ "/" + upDown +  "/" + lotId + "-CP2");
 	        FTPFile[] ftpFiles = ftpClient.listFiles();
 	        File cp2Folder = new File(localDirectory + upDown + "/" + lotId + "-CP2");
 			if (!cp2Folder.exists()) {
 				cp2Folder.mkdirs();
 			}
 		    for (int i = 0; i < ftpFiles.length ; i++) {
 		    	ftpClient.retrieveFile( new String(ftpFiles[i].getName().getBytes(), "iso-8859-1"), 
					      new DataOutputStream(new FileOutputStream(localDirectory + upDown + "/" + lotId + "-CP2" + "/" +ftpFiles[i].getName())));
 		    	CP2MapsPaths.add(localDirectory + upDown + "/" + lotId + "-CP2" + "/" +ftpFiles[i].getName());
 		    }
 		    File[] cp2files = cp2Folder.listFiles();
 		    if (CP1MapsPaths.size() != CP2MapsPaths.size()) {
 		    	throw new RuntimeException("CP1和CP2文件个数不匹配,请检查");
 		    }
 		    List<TSKInfoDTO> CP4 = new ArrayList();
 		    for (int i = 0; i < CP1MapsPaths.size(); i++)
            {
 		    	TSKInfoDTO tskInfo1 = binMapFileBean((String)CP1MapsPaths.get(i));
 	            TSKInfoDTO tskInfo2 = binMapFileBean((String)CP2MapsPaths.get(i));
 	            CP4.add(combineCP1AndCP2(tskInfo1, tskInfo2));
            }
 		    CPLot cpLot = new CPLot();
 	    	cpLot = cpLotApplication.findByLotNumber(lotId);
 	    	if ( cpLot==null ) {
 	    		throw new RuntimeException("Lot不存在，请确认！");
 	    	} 
 	    	String producttype = cpLot.getCustomerCPLot().getCustomerProductNumber();
 	    	//获取类文件所在的路径，为获取web应用路径做准备
	 		String classPath = this.getClass().getClassLoader().getResource("").getPath();
	 		//获取模板路径与导出文件的路径
	 		String templatePath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/";
	 		String exportPath = templatePath + "export/tsk/";
            if ("HYN11".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP4.template.xls";
            }
            else if ("HYN140".equals(producttype) || "RCS512X8".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP4_140.template.xls";
            }
            else if ("HYN150".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP4_150.template.xls";
            }
            FileInputStream fileIn = new FileInputStream(templatePath);
            // 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook(fileIn);
			List exportmaps = new ArrayList();
			Map<String, Object> sumData = new HashMap();
	        for ( int i = 0 ; i < CP4.size() ; i++ ) {
	        	TSKInfoDTO tskInfo = CP4.get(i);
	        	exportmaps.add(tskInfo);
	        	sumData = mapToWorkSheetFromTempl(tskInfo, wb, "CP4",sumData);
	        }
	        exportSummary(wb, "CP4",exportmaps,sumData);
	        fileName = lotId + "CP4" + ".xls";
	        FileOutputStream fout = new FileOutputStream(exportPath + fileName);  
		    wb.write(fout);
		    fout.close();
 		} catch (SocketException e) {
 			e.printStackTrace();
 		} catch (IOException e) {
 			e.printStackTrace();
 		}
		return fileName;
	}
    
    public String export(String lotId,File[] files,String cpx,int fileCount) {
    	//获取类文件所在的路径，为获取web应用路径做准备
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		//获取模板路径与导出文件的路径
    	String templatePath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/";
    	String exportPath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/export/";
    	CPLot cpLot = new CPLot();
    	//cpLot = cpLotApplication.get(Long.parseLong(lotId));
    	cpLot = cpLotApplication.findByLotNumber(lotId);
    	if ( cpLot==null ) {
    		throw new RuntimeException("Lot不存在，请确认！");
    	} 
    	String producttype = cpLot.getCustomerCPLot().getCustomerProductNumber();
    	//String producttype = "HYN140";
        if ("CP1".equals(cpx))
        {
            if ("HYN11".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP1.template.xls";
            }
            else if ("HYN140".equals(producttype) || "RCS512X8".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP1.template_140.xls";
            }
            else if ("HYN150".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP1.template_150.xls";
            }
            else if ("HY151".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP1.template_151.xls";
            }
            else if ("HYN152".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP1.template_152.xls";
            }
            //HYN153
            else if ("HYN153".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP1.template_153.xlsx";
            }
        }
        else if ("CP2".equals(cpx))
        {
            if ("HYN11".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP2.template.xls";
            }
            else if ("HYN140".equals(producttype) || "RCS512X8".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP2.template_140.xls";
            }
            else if ("HYN150".equals(producttype))
            {
                templatePath = templatePath + "lotId-WaferID_CP2.template_150.xls";
            }
        }
        try {
        	 //HYN11Export export = new HYN11Export();
            FileInputStream fileIn = new FileInputStream(templatePath);
            // 第一步，创建一个webbook，对应一个Excel文件
			HSSFWorkbook wb = new HSSFWorkbook(fileIn);
			List alreadyIn = new ArrayList();
			List exportmaps = new ArrayList();
			Map<String, Object> sumData = new HashMap();
	        for ( int i = 0 ; i < files.length ; i++ ) {
	        	TSKInfoDTO tskInfo = binMapFileBean(files[i].getPath());
	        	exportmaps.add(tskInfo);
	        	if (alreadyIn.contains(tskInfo.getWafer_ID()))
	            {
	                continue;
	            }
	        	sumData = mapToWorkSheetFromTempl(tskInfo, wb, cpx,sumData);
	        	alreadyIn.add(tskInfo.getWafer_ID());
	        }
	        exportSummary(wb, cpx,exportmaps,sumData);
	        String fileName = "";
	        if ("HYN153".equals(producttype))
	        {
	        	fileName = lotId + cpx + ".xlsx" ;
	        	FileOutputStream fout = new FileOutputStream(exportPath + fileName);  
		        wb.write(fout);
		        fout.close();
	        } else {
	        	fileName = lotId + cpx + ".xls";
	        	FileOutputStream fout = new FileOutputStream(exportPath + fileName);  
		        wb.write(fout);
		        fout.close();
	        }
	        return fileName;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
    
    public Map<String, Object> mapToWorkSheetFromTempl(TSKInfoDTO tskInfo,
    		HSSFWorkbook wb,String cpx, Map<String, Object> sumData) {
    	HSSFSheet toSheet = wb.cloneSheet(1);
    	HSSFCellStyle cellStyle = wb.createCellStyle();
    	cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中  
    	wb.setSheetName(wb.getSheetIndex(toSheet), tskInfo.getWafer_ID());
    	toSheet.getRow(3).getCell(1).setCellValue(tskInfo.getDevice_Name());
    	toSheet.getRow(4).getCell(1).setCellValue(tskInfo.getLot_No());
    	toSheet.getRow(5).getCell(1).setCellValue(tskInfo.getWafer_ID());
    	toSheet.getRow(6).getCell(1).setCellValue(tskInfo.getWaferSize());
    	toSheet.getRow(7).getCell(1).setCellValue(tskInfo.getStandard_Orientation_Flat_Direction());
    	toSheet.getRow(8).getCell(1).setCellValue(tskInfo.getStart_Time());
    	toSheet.getRow(9).getCell(1).setCellValue(tskInfo.getEnd_Time());
    	toSheet.getRow(10).getCell(1).setCellValue(tskInfo.getLoad_Time());
    	toSheet.getRow(11).getCell(1).setCellValue(tskInfo.getUnLoad_Time());
    	int[][] mapWithoutBlank = getMapWithoutBlank(tskInfo);
    	Object[][] map2 = new Object[mapWithoutBlank.length][mapWithoutBlank[0].length];
        List binSummary = new ArrayList();
        for (int i = 0; i <= 64; i++)
        {
            binSummary.add(0);
        }
        for (int i = 0; i < map2.length; i++)
        {
            for (int j = 0; j < map2[0].length; j++)
            {
                if (mapWithoutBlank[i][j] == 0)
                {
                    continue;
                }
                map2[i][j] = mapWithoutBlank[i][j];
                //在此统计
                int binNum = map2[i][j] == null ? 0 : Integer.parseInt(map2[i][j].toString());
                int num = (int) binSummary.get(binNum);
                num ++ ;
                binSummary.set(binNum, num);
            }
        }
        int sheetrow = map2.length;
        int sheetColumn = map2[0].length;
        int lastsheetrow = 4 + map2.length-1;
        int lastsheetColumn = 7 + map2[0].length-1;
        for ( int p=sheetrow;p < lastsheetColumn;p++) {
        	toSheet.setColumnWidth(p, 2*250);
        }
        //Range r = lastSheet.get_Range(lastSheet.Cells[4, 7], lastSheet.Cells[4 + map2.GetLength(0) - 1, 7 + map2.GetLength(1) - 1]);
        //r.Value2 = map2;
        for (int a = 4 ;a <= lastsheetrow ; a++ ) {
        	for (int b = 7 ;b <= lastsheetColumn ; b++ ) {
        		if ( map2[a-4][b-7] != null && !"".equals(String.valueOf(map2[a-4][b-7]))){
        			//toSheet.getRow(a-1).getCell(b-1).setCellValue(String.valueOf(map2[a-4][b-7]));
        			//HSSFRow  row = toSheet.createRow(a-1);
        			HSSFCell  cell = toSheet.getRow(a-1).createCell(b-1);
        			cell.setCellValue(Integer.valueOf(String.valueOf(map2[a-4][b-7])));
        			cell.setCellStyle(cellStyle);
        		}
            }
        }

        Object[][] x = new Object[2][map2[0].length];
        Object[][] y = new Object[map2.length][2];
        for (int i = 0; i < x[0].length; i++)//x
        {
            if (i % 5 == 0)
            {
                x[0][i] = i;
                x[1][i] = "+";
            }
            else
            {
                x[1][i] = "-";
            }
        }
        for (int j = 0; j < y.length; j++)//y
        {
            if (j % 5 == 0)
            {
                y[j][0] = j;
                y[j][1] = "+";
            }
            else
            {
                y[j][1] = "-";
            }

        }
        //r = lastSheet.get_Range(lastSheet.Cells[2, 6], lastSheet.Cells[3, 6 + x.GetLength(1) - 1]);//获取矩形选择框
        //r.Value2 = x;
        //r = lastSheet.get_Range(lastSheet.Cells[3, 5], lastSheet.Cells[3 + y.GetLength(0) - 1, 6]);//获取矩形选择框
        //r.Value2 = y;
        for (int c = 2 ;c <= 3 ; c++ ) {
        	for (int d = 6 ;d <= 6 + x[0].length - 1 ; d++ ) {
        		if ( x[c-2][d-6] != null ){
        			//HSSFRow  row = toSheet.createRow(c-1);
        			HSSFCell  cell = toSheet.getRow(c-1).createCell(d-1);
        			if ("+".equals(x[c-2][d-6].toString()) || "-".equals(x[c-2][d-6].toString())) {
        				cell.setCellValue(x[c-2][d-6].toString());
        			} else {
        				cell.setCellValue(Integer.valueOf(x[c-2][d-6].toString()));
        			}
        			cell.setCellStyle(cellStyle);
        		}
            }
        }
        for (int p = 3 ;p <= (3 + y.length - 1) ; p++ ) {
        	for (int q = 5 ;q <= 6 ; q++ ) {
        		if ( y[p-3][q-5] != null ){
        			//toSheet.getRow(p-1).getCell(q-1).setCellValue(y[p-3][q-5].toString());
        			//HSSFRow  row = toSheet.createRow(p-1);
        			HSSFCell  cell = toSheet.getRow(p-1).createCell(q-1);
        			if ("+".equals(y[p-3][q-5].toString()) || "-".equals(y[p-3][q-5].toString())) {
        				cell.setCellValue(y[p-3][q-5].toString());
        			} else {
        				cell.setCellValue(Integer.valueOf(y[p-3][q-5].toString()));
        			}
        			cell.setCellStyle(cellStyle);
        		}
            }
        }
        //Wafer Test Summary
        int row = 19;
        int gross_die = 0;

        for (int binNum = 0; binNum <= 64; binNum++)
        {
            gross_die += (int)binSummary.get(binNum);
            toSheet.getRow(row-1).getCell(1).setCellValue((int)(binSummary.get(binNum)));
            row++;
        }

        switch(cpx)
        {
            case "CP1":
                toSheet.getRow(13).getCell(1).setCellValue((int)binSummary.get(1) + (int)binSummary.get(2)); 
                toSheet.getRow(14).getCell(1).setCellValue(gross_die - (int)binSummary.get(1) - (int)binSummary.get(2));
                break;

            case "CP2":
            	toSheet.getRow(13).getCell(1).setCellValue((int)binSummary.get(1) + (int)binSummary.get(3));
            	toSheet.getRow(14).getCell(1).setCellValue(gross_die - (int)binSummary.get(1) - (int)binSummary.get(3));
                break;

            case "CP4":
            	toSheet.getRow(13).getCell(1).setCellValue((int)binSummary.get(1) + (int)binSummary.get(2) + (int)binSummary.get(3));
            	toSheet.getRow(14).getCell(1).setCellValue(gross_die - (int)binSummary.get(1) - (int)binSummary.get(2) - (int)binSummary.get(3));
                break;

            default:
            	toSheet.getRow(12).getCell(1).setCellValue(tskInfo.getTested_dice());
            	toSheet.getRow(13).getCell(1).setCellValue(tskInfo.getTested_pass_dice());
            	toSheet.getRow(14).getCell(1).setCellValue(tskInfo.getTested_fail_dice());
                break;
        }
        toSheet.getRow(12).getCell(1).setCellValue(gross_die); 
        toSheet.setForceFormulaRecalculation(true);
        sumData.put(tskInfo.getWafer_ID(), binSummary);
        return sumData;
    }
    
    public TSKInfoDTO binMapFileBean(String file) {
    	FileInputStream fileIn = null;
    	DataInputStream in = null;
    	TSKInfoDTO tskinfo = new TSKInfoDTO();
		try {
			File f = new File(file);
			byte[] readBuff = new byte[(int)f.length()];
			fileIn = new FileInputStream(file);
			in = new DataInputStream(fileIn); 
			in.read(readBuff);
			String operator_Name = getByte(readBuff,1,20);  
		    String device_Name = getByte(readBuff,21,36);
		    int Wafer_Size = getIntBinary(readBuff,37,38);
		    int Machine_No = getIntBinary(readBuff,39,40);
		    int index_Size_X = getIntBinary(readBuff,41,44);
		    int index_Size_Y = getIntBinary(readBuff,45,48);
		    double standard_Orientation_Flat_Direction = getIntBinary(readBuff,49,50);
		    int final_Editing_Machine = getIntBinary(readBuff,51,51);
		    int map_Version = getIntBinary(readBuff,52,52);
		    long columnsize = getIntBinary(readBuff,53,54);
		    long rowsize = getIntBinary(readBuff,55,56);
		    if ("P7241D".equals(device_Name))
            {
                columnsize += 2L;
                rowsize += 2L;
            }
		    int map_Data_Form = getIntBinary(readBuff,57,60);
		    String wafer_ID = getByte(readBuff,61,81);
		    int number_of_Probing = getIntBinary(readBuff,82,82);
		    String lot_No = getByte(readBuff,83,100);
		    int cassette_No = getIntBinary(readBuff,101,102);
		    int slot_No = getIntBinary(readBuff,103,104);
		    int x_axis_coordinates_increase_direction = getIntBinary(readBuff,105,105);
		    int y_axis_coordinates_increase_direction = getIntBinary(readBuff,106,106);
		    int reference_die_setting_procedures = getIntBinary(readBuff,107,107);
		    int reserved_108 = getIntBinary(readBuff,108,108);
		    int target_die_position_X = getIntBinary(readBuff,109,112);
		    int target_die_position_Y = getIntBinary(readBuff,113,116);
		    int refdieX = getIntBinary(readBuff,117,118);
		    if (refdieX > 20)
            {
                refdieX = 1;
            }
		    int refdieY = getIntBinary(readBuff,119,120);
		    int probing_start_position = getIntBinary(readBuff, 121, 121);
		    int probing_direction = getIntBinary(readBuff, 122, 122);
		    int reserved_123_124 = getIntBinary(readBuff, 123, 124);
		    int distance_X_to_wafer_center_die_origin = getIntBinary(readBuff, 125, 128);
		    int distance_Y_to_wafer_center_die_origin = getIntBinary(readBuff, 129, 132);
		    int coordinator_X_of_wafer_center_die = getIntBinary(readBuff, 133, 136);
		    int coordinator_Y_of_wafer_center_die = getIntBinary(readBuff, 137, 140);
		    int first_Die_Coordinator_X = getIntBinary(readBuff, 141, 144);
		    String startTime_Year = getByte(readBuff, 149, 150);
		    String startTime_Month = getByte(readBuff, 151, 152);
		    String startTime_Day = getByte(readBuff, 153, 154);
		    String startTime_Hour = getByte(readBuff, 155, 156);
		    String startTime_Minute = getByte(readBuff, 157, 158);
		    int startTime_Reserve = getIntBinary(readBuff, 159, 160);
		    String endTime_Year = getByte(readBuff, 161, 162);
		    String endTime_Month = getByte(readBuff, 163, 164);
		    String endTime_Day = getByte(readBuff, 165, 166);
		    String endTime_Hour = getByte(readBuff, 167, 168);
		    String endTime_Minute = getByte(readBuff, 169, 170);
		    int endTime_Reserve = getIntBinary(readBuff, 171, 172);
            String loadTime_Year = getByte(readBuff, 173, 174);
            String loadTime_Month = getByte(readBuff, 175, 176);
            String loadTime_Day = getByte(readBuff, 177, 178);
            String loadTime_Hour = getByte(readBuff, 179, 180);
            String loadTime_Minute = getByte(readBuff, 181, 182);
            int loadTime_Reserve = getIntBinary(readBuff, 183, 184);
            String unLoadTime_Year = getByte(readBuff, 185, 186);
            String unLoadTime_Month = getByte(readBuff, 187, 188);
            String unLoadTime_Day = getByte(readBuff, 189, 190);
            String unLoadTime_Hour = getByte(readBuff, 191, 192);
            String unLoadTime_Minute = getByte(readBuff, 193, 194);
            int unLoadTime_Reserve = getIntBinary(readBuff, 195, 196);
            int machine_No_1 = getIntBinary(readBuff, 197, 200);
            int machine_No_2 = getIntBinary(readBuff, 201, 204);
            int special_Characters = getIntBinary(readBuff, 205, 208);
            int testing_End_Information = getIntBinary(readBuff, 209, 209);
            int reserve_210 = getIntBinary(readBuff, 210, 210);
            int tested_dice = getIntBinary(readBuff, 211, 212);
            int tested_pass_dice = getIntBinary(readBuff, 213, 214);
            int tested_fail_dice = getIntBinary(readBuff, 215, 216);
            int test_Die_Information_Address = getIntBinary(readBuff, 217, 220);
            int number_of_line_category_data = getIntBinary(readBuff, 221, 224);
            int line_category_address = getIntBinary(readBuff, 225, 228);
            String map_File_Configuration = byteToBinary(readBuff, 229, 230);
            int max_Multi_Site = getIntBinary(readBuff, 231, 232);
            int max_Categories = getIntBinary(readBuff, 233, 234);
            int reserve_235236 = getIntBinary(readBuff, 235, 236);
            int nTotalChips = (int)(columnsize * rowsize);
            int Byte_of_Header_Information = 236;
            int Byte_of_Extended_Header_Information = 172;

            // Map_File_Configuration
            int availability_of_Header_Information = mid(map_File_Configuration, 16, 17);
            int availability_of_Test_Result_Information_per_Die = mid(map_File_Configuration, 15, 16);
            int availability_of_Line_Category_Information = mid(map_File_Configuration, 14, 15);
            int availability_of_Extension_Header_Information = mid(map_File_Configuration, 13, 14);
            int availability_of_Test_Result_Information_per_Extension_Die = mid(map_File_Configuration, 12, 13);
            int availability_of_Extension_Line_Category_Information = mid(map_File_Configuration, 11, 12);
            int tsk_File_Size = (availability_of_Header_Information * Byte_of_Header_Information
                    + availability_of_Test_Result_Information_per_Die * nTotalChips * 6
                    + availability_of_Line_Category_Information * nTotalChips * 8);
            int map[][][] = new int[(int) (rowsize + 1L)][ (int) (columnsize + 1L)][2];
            long r_y = 0;
            for (int p = 1; p <= rowsize; p++)
            {
                for (int q = 1; q <= columnsize; q++)
                {
                    int startPos = (int) (((test_Die_Information_Address) + (6 * (((p - 1) * ((int)columnsize)) + q - 1))) + 1);
                    int length = (int) (((test_Die_Information_Address) + (6 * (((p - 1) * ((int)columnsize)) + q - 1))) + 6);
                    String onedieresult = byteToBinary(readBuff, startPos, length).toString();
                    int x = Integer.parseInt(onedieresult.substring(8, 17),2);
                    int y = Integer.parseInt(onedieresult.substring(24, 33), 2);
                    tskinfo.setX(x);
                    tskinfo.setY(y);
                    int category = Integer.parseInt(onedieresult.substring(43, 49), 2);
                    long testDieVal = Long.parseLong(onedieresult.substring(17, 19), 2);
                    long passDieVal = Long.parseLong(onedieresult.substring(1, 3), 2);
                    boolean isTestDie;
                    boolean isPassDie;

                    if (testDieVal == 1)
                    {
                        isTestDie = true;
                    }
                    else
                    {
                    	isTestDie = false;
                    }
                    if (passDieVal == 1)
                    {
                    	isPassDie = true;
                    }
                    else
                    {
                    	isPassDie = false;
                    }

                    if (r_y >= 511 && q == (int)columnsize)
                    {
                        r_y++;
                    }
                    else if (r_y < 511)
                    {
                        r_y = y;
                    }

                    if (isTestDie)
                    {
                        int m = 0;
                        int n = 0;
                        if (refdieY <= 1)
                        {
                            n = (int)((x - refdieX)) + 1;
                           m = (int)((r_y - refdieY)) + 1;
                        }
                        else
                        {
                            n = x;
                          m = (int)r_y;
                        }
                        map[m][n][(int)(0L)] = (int)category;

                        if (isPassDie)
                        {
                        	map[m][n][(int)(1L)] = 4;
                        }
                        else
                        {
                        	map[m][n][(int)(1L)] = 3;
                        }
                    }
                }
            }
            int passbin2 = 0;
            int passbin3 = 0;
            int passbin4 = 0;
            int num3 = 0;
            int num4 = 0;
            int num7 = 2;
            int num8 = 0;
            int num9 = 0;
            for (num3 = 0; num3 < rowsize; num3++)
            {
                for (num4 = 0; num4 < columnsize; num4++)
                {
                    if ((map[num3][num4][1] != 0L) && (((int)map[num3][num4][0]) == num7))
                    {
                        num8++;
                    }
                    if ((map[num3][num4][1] != 0L) && (((int)map[num3][num4][0]) == 3))
                    {
                        num9++;
                    }
                    if ((map[num3][num4][1] != 0L) && (((int)map[num3][num4][0]) == 4))
                    {
                      passbin4++;
                    }
                }
            }
            passbin2 = num8;
            passbin3 = num9;
            int tested_die = 0;
            int pass_die = 0;
            int fail_die = 0;
            int gooddie=0;
            for (int x = 0; x < (int) (rowsize + 1L); x++)
            {
                for (int y = 0; y < (int) (columnsize + 1L); y++)
                {
                    int category = map[x][y][1];
                    int binNum = map[x][y][0]; 
                    switch (category)
                    {
                        case 0://not test die
                            break;

                        case 4://pass die
                            tested_die++;
                            pass_die++;
                            break;

                        case 3://fail die
                            tested_die++;
                            fail_die++;
                            break;

                        default:
                            break;
                    }
                }
            }
            tskinfo.setTotal_Dice(tested_die);
            tskinfo.setTotal_Pass_Dice(pass_die);
            tskinfo.setTotal_Fail_Dice(fail_die);
            if (tested_die != 0 && pass_die != 0)
            {
            	tskinfo.setTotal_Yield((String.format("%g",(Float.parseFloat(String.valueOf(pass_die)) * 100) / Integer.parseInt(String.valueOf(tested_die)))) + "%");
            }
            tskinfo.setPassbin2(String.valueOf(passbin2));
            tskinfo.setPassbin3(String.valueOf(passbin3));
            tskinfo.setPassbin4(String.valueOf(passbin4));
            tskinfo.setWafer_ID(wafer_ID);
            tskinfo.setLot_No(lot_No);
            tskinfo.setDevice_Name(device_Name);
            tskinfo.setOperator_Name(operator_Name);
            tskinfo.setIndex_X(index_Size_X);
            tskinfo.setIndex_Y(index_Size_Y);
            tskinfo.setMap(map);
            tskinfo.setWaferSize(Wafer_Size);
            tskinfo.setStandard_Orientation_Flat_Direction(standard_Orientation_Flat_Direction);
            tskinfo.setRowSize(rowsize);
            tskinfo.setColumnsize(columnsize);
            tskinfo.setTested_dice(tested_dice);
            tskinfo.setTested_pass_dice(tested_pass_dice);
            tskinfo.setTested_fail_dice(tested_fail_dice);
            if ((startTime_Year != null && startTime_Year != "") 
            		&& (startTime_Month != null && startTime_Month != "")
            		&& (startTime_Day != null && startTime_Day != "")
            		&& (startTime_Hour != null && startTime_Hour != "")
            		&& (startTime_Minute != null && startTime_Minute != "")) {
            	//tskinfo.setStart_Time(startTime_Year + "-" + startTime_Month + "-" 
            	//	+ startTime_Day + "-" + startTime_Hour + "-" + startTime_Minute);
            	tskinfo.setStart_Time(String.format("%s-%s-%s %s:%s", 
            			startTime_Year,startTime_Month,startTime_Day,startTime_Hour,startTime_Minute));
            }
            if ((endTime_Year != null && endTime_Year != "") 
            		&& (endTime_Month != null && endTime_Month != "")
            		&& (endTime_Day != null && endTime_Day != "")
            		&& (endTime_Hour != null && endTime_Hour != "")
            		&& (endTime_Minute != null && endTime_Minute != "")) {
            	//tskinfo.setEnd_Time(endTime_Year + "-" + endTime_Month + "-" 
	            //		+ endTime_Day + "-" + endTime_Hour + "-" + endTime_Minute);
            	tskinfo.setEnd_Time(String.format("%s-%s-%s %s:%s", 
            			endTime_Year,endTime_Month,endTime_Day,endTime_Hour,endTime_Minute));
            }
            if ((loadTime_Year != null && loadTime_Year != "") 
            		&& (loadTime_Month != null && loadTime_Month != "")
            		&& (loadTime_Day != null && loadTime_Day != "")
            		&& (loadTime_Hour != null && loadTime_Hour != "")
            		&& (loadTime_Minute != null && loadTime_Minute != "")) {
            	//tskinfo.setLoad_Time(loadTime_Year + "-" + loadTime_Month + "-" 
	            //		+ loadTime_Day + "-" + loadTime_Hour + "-" + loadTime_Minute);
            	tskinfo.setLoad_Time(String.format("%s-%s-%s %s:%s", 
            			loadTime_Year,loadTime_Month,loadTime_Day,loadTime_Hour,loadTime_Minute));
            }
            if ((unLoadTime_Year != null && unLoadTime_Year != "") 
            		&& (unLoadTime_Month != null && unLoadTime_Month != "")
            		&& (unLoadTime_Day != null && unLoadTime_Day != "")
            		&& (unLoadTime_Hour != null && unLoadTime_Hour != "")
            		&& (unLoadTime_Minute != null && unLoadTime_Minute != "")) {
            	//tskinfo.setUnLoad_Time(unLoadTime_Year + "-" + unLoadTime_Month + "-" 
	            //		+ unLoadTime_Day + "-" + unLoadTime_Hour + "-" + unLoadTime_Minute);
            	tskinfo.setUnLoad_Time(String.format("%s-%s-%s %s:%s", 
            			unLoadTime_Year,unLoadTime_Month,unLoadTime_Day,unLoadTime_Hour,unLoadTime_Minute));
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return tskinfo;
	}
    
    public int[][] getMapWithoutBlank(TSKInfoDTO tskInfo)
    {
        List blankRowIndexs = new ArrayList();
        int xs = (int)(tskInfo.getRowSize() + 1L);
        int ys = (int)(tskInfo.getColumnsize() + 1L);
        int map[][][] = tskInfo.getMap();
        int leftBlank = ys;
        //数出所有空行和左侧空列
        for (int i = 0; i < xs; i++)
        {
            boolean isBlankRow = true;
            for (int j = 0; j < ys; j++)
            {
                if (map[i][j][0] != 0)
                {
                    isBlankRow = false;
                    if (leftBlank >= j) { leftBlank = j; }//获取左侧空白
                    break;
                }
            }
            if (isBlankRow)
            {
                blankRowIndexs.add(i);
            }
        }
        int rightBlank = 0;
        for (int i = 0; i < xs; i++)
        {
            for (int j = ys - 1; j >= 0; j--)
            {
                if (map[i][j][0] != 0)
                {
                    if (rightBlank <= j) { rightBlank = j; }//获取左侧空白
                    break;
                }
            }
        }
      
        int newxs = xs - blankRowIndexs.size();
        int newys = rightBlank - leftBlank + 1;
        int[][] newMap;
        if (newxs <= 0 || newys <= 0)
        {
            newMap = new int[xs][ys];
        } else {
        	newMap = new int[newxs][newys];
            
            int m = 0, n = 0;
            for (int i = 0; i < xs; i++)
            {
                if (blankRowIndexs.contains(i))
                {
                    continue;
                }
                for (int j = 0; j < ys; j++)
                {
                    if (j < leftBlank || j > rightBlank)
                    {
                        continue;
                    }
                    else
                    {
                        newMap[m][n++] = map[i][j][0];
                    }

                }
                n = 0;
                m++;
            }
        }
        return newMap;
    }
    
    public void exportSummary(HSSFWorkbook wb,String cpx, List exportmaps, Map<String, Object> sumData)
    {
        HSSFSheet summarySheet = wb.getSheetAt(0);
        Object[][] cells = new Object[exportmaps.size()][67];
        long totalDie = 0;
        long passDie = 0;
        long failDie = 0;
        //数据
        for (int i = 0; i < exportmaps.size(); i++)
        {
            TSKInfoDTO tskinfo = (TSKInfoDTO) exportmaps.get(i);
            List currentData = (List) sumData.get(tskinfo.getWafer_ID());
            int column = 0;
            cells[i][column++] = tskinfo.getWafer_ID();
            long tested_die = 0;
            long pass_die = 0;
            
            column++;
            for (int v=0;v < currentData.size(); v++)
            {
                cells[i][column++] = currentData.get(v);
            }
            if ("CP2".equals(cpx))
            {
                pass_die = Long.parseLong(cells[i][3].toString()) + Long.parseLong(cells[i][5].toString());
            }

            else if ("CP1".equals(cpx))
            {
                pass_die = Long.parseLong(cells[i][3].toString()) + Long.parseLong(cells[i][4].toString());
            }
            if ("CP4".equals(cpx))//CP4的导出EXCEL
            {
                pass_die = Long.parseLong(cells[i][3].toString()) + Long.parseLong(cells[i][4].toString()) + Long.parseLong(cells[i][5].toString());
            }
            for (int bin = 2; bin <= 66; bin++ )
            {
                tested_die += Long.parseLong(cells[i][bin].toString());
            }
            cells[i][1] = (float)pass_die / (float)tested_die * 100;
            DecimalFormat df = new DecimalFormat("#0.00");   
            cells[i][1] = df.format(Double.parseDouble(cells[i][1].toString()));
            cells[i][1] = cells[i][1] + "%";
            totalDie += tested_die;
            if ("CP2".equals(cpx))
            {
                passDie += Long.parseLong(cells[i][3].toString()) + Long.parseLong(cells[i][5].toString());
                failDie += tested_die - Long.parseLong(cells[i][3].toString()) - Long.parseLong(cells[i][5].toString());
            }
            
            else if ("CP1".equals(cpx))
            {
                passDie += Long.parseLong(cells[i][3].toString()) + Long.parseLong(cells[i][4].toString());
                failDie += tested_die - Long.parseLong(cells[i][3].toString()) - Long.parseLong(cells[i][4].toString());
            }
            if ("CP4".equals(cpx))//CP4的导出EXCEL
            {
                passDie += Long.parseLong(cells[i][3].toString()) + Long.parseLong(cells[i][4].toString()) + Long.parseLong(cells[i][5].toString());
                failDie += tested_die - Long.parseLong(cells[i][3].toString()) - Long.parseLong(cells[i][4].toString()) - Long.parseLong(cells[i][5].toString());
            }
        }

        //头信息
        TSKInfoDTO mapFirst = (TSKInfoDTO) exportmaps.get(0);
        if ("CP4".equals(cpx))
        {
            mapFirst.setDevice_Name( mapFirst.getDevice_Name().substring(0, mapFirst.getDevice_Name().length() - 1) + "4");
        }
        summarySheet.getRow(0).getCell(2).setCellValue(mapFirst.getDevice_Name());
        summarySheet.getRow(1).getCell(2).setCellValue("ACETEC");
        summarySheet.getRow(1).getCell(8).setCellValue(mapFirst.getMachine_No());
        summarySheet.getRow(2).getCell(5).setCellValue(mapFirst.getLot_No());
        summarySheet.getRow(2).getCell(8).setCellValue(exportmaps.size());
        summarySheet.getRow(2).getCell(10).setCellValue(mapFirst.getStart_Time());
        summarySheet.getRow(3).getCell(2).setCellValue(totalDie);
        summarySheet.getRow(3).getCell(5).setCellValue(passDie);
        summarySheet.getRow(3).getCell(8).setCellValue((float)passDie / (float)totalDie);
        summarySheet.getRow(3).getCell(10).setCellValue(failDie);
        //Range r = summarySheet.get_Range(summarySheet.Cells[7, 1], summarySheet.Cells[this.exportMaps.Count + 7 - 1, 67]);
        //r.Value2 = cells;
        for (int a = 7 ;a <= exportmaps.size() + 6 ; a++ ) {
        	for (int b = 1 ;b <= 67 ; b++ ) {
        		if ( cells[a-7][b-1] != null && String.valueOf(cells[a-7][b-1]) != null){
        			if (cells[a-7][b-1].toString().contains("-") || cells[a-7][b-1].toString().contains("%")) {
        				summarySheet.getRow(a-1).getCell(b-1).setCellValue((cells[a-7][b-1]).toString());
        			} else {
        				summarySheet.getRow(a-1).getCell(b-1).setCellValue(Integer.valueOf((cells[a-7][b-1]).toString()));
        			}
        		}
            }
        }
        summarySheet.setForceFormulaRecalculation(true);
    }
    
    @Override
	public String exportInkList(String upDown,String directoryName,List<TSKInfoDTO> tskInfoDTOs) {
    	 //if (tskInfoDTOs == null) {
    	 //	 throw new RuntimeException("请先双击选择左侧一个以'-CP2'结尾的Lot.");
    	 //}
    	 String zipName = "";
    	 String device = "";
    	 //String device = "HYN140";
    	 String lotId = directoryName;
    	 String lot = lotId.replace("-CP2", "");
    	 CPLot cpLot = new CPLot();
     	 cpLot = cpLotApplication.findByLotNumber(lot);
     	 if (cpLot != null) {
     		device = cpLot.getCustomerCPLot().getCustomerProductNumber();
     	 } else {
     		throw new RuntimeException("Lot不存在，请确认！");
     	 }
    	 if (!lotId.endsWith("-CP2") && !"HY151".equals(device)) {
    		 throw new RuntimeException("InkList导出必须选择左侧列表中以-CP2结尾的Lot");
    	 }
    	 List tskMapFileNames = new ArrayList();
         List CP1MapsPaths = new ArrayList();
     	 FTPClient ftpClient = new FTPClient();
     	 File totalFile = new File(localDirectory + upDown + "/" + directoryName);
     	 File[] files = totalFile.listFiles();
     	 for (int j = 0 ; j < files.length ; j++) {
     		tskMapFileNames.add(files[j].getPath());
     	 }
         if ("HYN140".equals(device) || "RCS512X8".equals(device)) {
        	 String remotePath = "/map/up/" + lot+ "-CP1/";
             String localPath = localDirectory + "/TSKTem/" + lot + "-CP1/";
             File folder = new File(localPath);
    	     if (!folder.exists()) {
    	    	 folder.mkdirs();
    		 }
    	     try {
				 ftpClient.connect(server);
				 ftpClient.login(ftpUserName, ftpUserPwd);
	 			 ftpClient.setDefaultTimeout(15000);
	 		     ftpClient.setSoTimeout(30000);
	 			 ftpClient.setDataTimeout(15000);
			 } catch (SocketException e1) {
				e1.printStackTrace();
			 } catch (IOException e1) {
				e1.printStackTrace();
			 }
 			 int reply = ftpClient.getReplyCode();
 			 if (!FTPReply.isPositiveCompletion(reply)) {
 			    throw new RuntimeException("连接FTP服务器失败!");
 			 }
             try {
				ftpClient.changeWorkingDirectory(remotePath);
				 FTPFile[] ftpFiles = ftpClient.listFiles();
				 for ( int i =0; i < ftpFiles.length;i++) {
	            	 if(ftpFiles[i].getName().contains("=")){
	            		 continue;
	            	 } else {
	            		 ftpClient.retrieveFile( new String(ftpFiles[i].getName().getBytes(), "iso-8859-1"), 
	 							new DataOutputStream(new FileOutputStream(localPath +ftpFiles[i].getName())));
	                     CP1MapsPaths.add(localPath + ftpFiles[i].getName());
	            	 }
	             }
			 } catch (IOException e) {
				throw new RuntimeException("FTP文件路径出错,请检查是否存在该Lot的CP1文件夹");
			 }
             if (tskMapFileNames.size()!= CP1MapsPaths.size())
             {
                 throw new RuntimeException("CP1和CP2文件个数不匹配,请检查");
             }
             zipName = exportInkHYN140(tskMapFileNames,CP1MapsPaths,lot);
         } else {
        	 zipName = exportINK(tskMapFileNames,lot);
         }
         return zipName;
	}
    
    private String exportInkHYN140(List tskMapFileNames, List cP1MapsPaths ,String lot)
    {
        List tskMapFileNamesCP1 = cP1MapsPaths;
        List tskMapFileNamesCP2 = tskMapFileNames;
        //获取类文件所在的路径，为获取web应用路径做准备
		String classPath = this.getClass().getClassLoader().getResource("").getPath();
		//获取模板路径与导出文件的路径
    	String templatePath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/export/inkList/";
    	String fileName = FilenameHelper.generateDatetimeBasedFilename(lot,"");
        File zipFile = new File(templatePath+ "\\" + fileName + ".zip"); 
        Project prj = new Project();    
        Zip zip = new Zip();    
        zip.setProject(prj);    
        zip.setDestFile(zipFile);
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj); 
    	File folder = new File(templatePath);
		if (!folder.exists()) {
		    folder.mkdirs();
		}
        String device = "";
        LinkedList suminfo = new LinkedList();
        for (int i = 0; i < tskMapFileNamesCP1.size(); i++)
        {
            TSKInfoDTO tskInfo1 = binMapFileBean((String) tskMapFileNamesCP1.get(i));
            TSKInfoDTO tskInfo2 = binMapFileBean((String) tskMapFileNamesCP2.get(i));
            TSKInfoDTO tskInfo = combineCP1AndCP2(tskInfo1, tskInfo2);
            suminfo = HYN11_ExportInkList(tskInfo, templatePath + "\\" + tskInfo.getWafer_ID() + ".txt",suminfo);
            File srcdir = new File(templatePath);
            if (!srcdir.exists()){  
                throw new RuntimeException("文件夹不存在！");    
            }
            fileSet.setDir(srcdir);
            fileSet.setIncludes("**/" + tskInfo.getWafer_ID() + ".txt");
            device = tskInfo.getDevice_Name().split("-")[0];
        }
        ExportInkSummary(templatePath, device,suminfo);
        fileSet.setIncludes("**/" + hynfilename + ".HYN");
        zip.addFileset(fileSet);
        zip.execute();  
        return zipFile.getName();
    }
    
    public TSKInfoDTO combineCP1AndCP2(TSKInfoDTO cp1, TSKInfoDTO cp2)
    {
        for (int i = 0; i < cp2.getMap().length; i++)
        {
            for (int j = 0; j < cp2.getMap()[0].length; j++)
            {
                int category_CP2 = cp2.getMap()[i][j][1];
                int category_CP1 = cp1.getMap()[i][j][1];
                int binNum_CP2 = cp2.getMap()[i][j][0];
                int binNum_CP1 = cp1.getMap()[i][j][0];

                if (category_CP1 == 3 || category_CP2 == 3)
                {
                    //fail die,任何一个map的die fail掉,则结果为fail,CP1中fail die以cp1为结果,CP2中的fail(Not bin59)以CP2中为准
                    cp2.getMap()[i][j][1] = 3;
                    if (binNum_CP2 == 59)//这是在CP1中Fail掉了,以CP1为准
                    {
                        cp2.getMap()[i][j][0] = binNum_CP1;
                        continue;
                    }
                    //其他状况保留CP2的Fail即可
                }

                if (category_CP2 == 0 || category_CP1 == 0)
                {
                    //not test die 直接下一个
                    continue;
                }

                if (category_CP1 == 4 && category_CP2 == 4)//pass die
                {
                    if( binNum_CP1 < binNum_CP2 ){
                    	cp2.getMap()[i][j][0] = binNum_CP2;
                    } else {
                    	cp2.getMap()[i][j][0] = binNum_CP1;
                    }
                    continue;
                }
            }
        }
        return cp2;
    }
    
    public LinkedList HYN11_ExportInkList(TSKInfoDTO cp2MapFile, String tgtInkFilePath, LinkedList suminfo)
    {
        StringBuilder sb = new StringBuilder();
        LinkedList fileContents = new LinkedList();
        if (suminfo.size() == 0)
        {
            suminfo.add("MAPPING\t\tWAFER_ID\tBIN 1\tBIN 2\tBIN 3");
        }
        
        String fileHeader = "//HYNITRON\r\n";
        if (cp2MapFile.getDevice_Name().indexOf("HY151") > -1)
        {
            hynfilename = cp2MapFile.getLot_No().replace("-CP1", "");
            fileHeader += "//" + cp2MapFile.getDevice_Name().replace("-CP1", "") + "\r\n//" + cp2MapFile.getLot_No().replace("-CP1", "") + "\r\n//" + cp2MapFile.getWafer_ID() + "\r\n";
        }
        else
        {
            hynfilename = cp2MapFile.getLot_No().replace("-CP2", "");
            fileHeader += "//" + cp2MapFile.getDevice_Name().replace("-CP2", "") + "\r\n//" + cp2MapFile.getLot_No().replace("-CP2", "") + "\r\n//" + cp2MapFile.getWafer_ID() + "\r\n";
        }
        //fileHeader += "//Notch(degree) :" + cp2MapFile.Standard_Orientation_Flat_Direction.ToString("000") + " Down\r\n";
        fileHeader += "//Notch(degree) :" + String.valueOf(cp2MapFile.getStandard_Orientation_Flat_Direction()) + " Down\r\n";
        //fileHeader += "//_Row Count :" + cp2MapFile.y.ToString("000") + "\r\n";
        fileHeader += "//_Row Count :" + String.valueOf(cp2MapFile.getY()) + "\r\n";
        //fileHeader += "//_Column count :" + cp2MapFile.x.ToString("000") + "\r\n";
        fileHeader += "//_Column count :" + String.valueOf(cp2MapFile.getX()) + "\r\n";
        int[][][] map = cp2MapFile.getMap();
        //开始转换其中的Pass or Fail DIE 
        int bin1 = 0, bin2 = 0, bin3 = 0;
        for (int i = 0; i < map.length; i++)
        {
            String line = "";
            for (int j = 0; j < map[0].length; j++)
            {
                int category = map[i][j][1];
                int binNum = map[i][j][0]; 
                if ((i == 75 && j == 10) || (i == 75 && j == 92)){
                    category = category;
                }
                switch (category)
                {
                    case 0://not test die
                        if (((i == 12 && j == 20) || (i == 12 && j == 93)) && cp2MapFile.getDevice_Name().split("-")[0] == "HYN140")
                        {
                            line += "#";
                        }
                        else if (((i == 12 && j == 15) || (i == 12 && j == 70)) && cp2MapFile.getDevice_Name().split("-")[0] == "HYN11")
                        {
                            line += "#";
                        }
                        else if (((i == 76 && j == 11) || (i == 76 && j == 93)) && cp2MapFile.getDevice_Name().split("-")[0] == "HYN150")
                        {
                            line += "#";
                        }
                        else
                        {
                            line += ".";
                        }
                        break;
                    case 4://pass die  
                         line += String.valueOf(binNum); 
                        switch (binNum)
                        {
                            case 1: bin1++; break;
                            case 2: bin2++; break;
                            case 3: bin3++; break;
                        } 
                        break;
                    case 3://fail die
                        line += "X";
                        break;
                    default:
                        line += "?";
                        break;
                }
            }
            sb.append(line);
            fileContents.addLast(line);
        }
        bin1_total += bin1;
        bin2_total += bin2;
        bin3_total += bin3;
        //lastLotId = cp2MapFile.Lot_No;
        if ("HYN140".equals(cp2MapFile.getDevice_Name().split("-")[0]) || 
            "HYN152".equals(cp2MapFile.getDevice_Name().split("-")[0]) || 
            "HYN153".equals(cp2MapFile.getDevice_Name().split("-")[0]))
        {
            lastLotId = "." + cp2MapFile.getWafer_ID().split("-")[1];
        }
        else if ("HYN150".equals(cp2MapFile.getDevice_Name().split("-")[0]))
        {
            //lastLotId = cp2MapFile.getWafer_ID().split("-")[1].Insert(cp2MapFile.getWafer_ID().split("-")[1].length() - 2, ".");
            lastLotId = cp2MapFile.getWafer_ID().split("-")[1].substring(0, cp2MapFile.getWafer_ID().split("-")[1].length() - 2) + "." +
                        cp2MapFile.getWafer_ID().split("-")[1].substring(cp2MapFile.getWafer_ID().split("-")[1].length() - 2,cp2MapFile.getWafer_ID().split("-")[1].length());
        }
        else
        {
            //lastLotId = cp2MapFile.getWafer_ID().split("-")[0].Insert(cp2MapFile.getWafer_ID().split("-")[0].length() - 2, ".");
        	lastLotId = cp2MapFile.getWafer_ID().split("-")[0].substring(0, cp2MapFile.getWafer_ID().split("-")[0].length() - 2) + "." +
        			    cp2MapFile.getWafer_ID().split("-")[0].substring(cp2MapFile.getWafer_ID().split("-")[0].length() - 2,cp2MapFile.getWafer_ID().split("-")[0].length());
        }
        String secondstr= hynfilename + lastLotId.split("[.]")[1];
        String firststr = hynfilename + "." + lastLotId.split("[.]")[1];
        if (secondstr.length() >= "WAFER_ID".length())
        {
            secondstr += "\t";
        }
        else
        {
            secondstr += "\t\t";
        }
        suminfo.add(firststr + "\t" + secondstr + bin1 + "\t" + bin2 + "\t" + bin3);
        int Pass = bin1 + bin2 + bin3;
        fileHeader += "//PASS = " + String.valueOf(Pass) + "\r\n";
        fileHeader += "//Number of bin1 = " + String.valueOf(bin1) + "\r\n";
        fileHeader += "//Number of bin2 = " + String.valueOf(bin2) + "\r\n";
        fileHeader += "//Number of bin3 =" + String.valueOf(bin3) + "\r\n";

        //此时的Map图含有多余的行和列,先不加文件头,去掉多余之后再加
        deleteBlankDot(fileContents);

        //加入文件头
        fileContents.addFirst(fileHeader);
        try {
        	File file = new File(tgtInkFilePath);
        	if (file.exists()) {
        		file.delete();
        	}
            file.createNewFile();
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            Object[] fileContentsArray = fileContents.toArray();
            for ( int j = 0; j < fileContents.toArray().length ; j++) {
            	bw.newLine();
            	bw.write(String.valueOf(fileContentsArray[j]));
            }
			bw.flush();
		    bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return suminfo;
    }
    
    public void ExportInkSummary(String savePath, String device, LinkedList suminfo)
    {
        String fileHeader = "HYNITRON\r\n" + device + "\r\n" + hynfilename + "\r\n" + (suminfo.size() - 1) + "\r\n";
        suminfo.addFirst(fileHeader);
        String lastLine = "\t\t\tTOTAL\t" + bin1_total + "\t" + bin2_total + "\t" + bin3_total;
        suminfo.addLast(lastLine);
        File file = new File(savePath + "\\" + hynfilename + ".HYN");
		try {
			if (file.exists()) {
        		file.delete();
        	}
			file.createNewFile();
	        FileWriter fw = new FileWriter(file, true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        Object[] suminfoArray = suminfo.toArray();
            for ( int i = 0; i < suminfoArray.length ; i++) {
            	bw.newLine();
            	bw.write(String.valueOf(suminfoArray[i]));
            }
			bw.flush();
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private void deleteBlankDot(LinkedList fileContents)
    {

        //1.删除空行
    	int cols = fileContents.getFirst().toString().length();
        StringBuilder blankLine = new StringBuilder();
        for (int i = 0; i < cols; i++)
        {
            blankLine.append(".");
        }
        while (fileContents.contains(blankLine.toString()))
        {
            fileContents.remove(blankLine.toString());
        }

        //2.删除左侧空列
        //2.1 数出左侧空余数量
        int leftBlank = cols;
        //Object[] array = fileContents.toArray();
        //foreach (String line in fileContents)
        for (int j = 0; j < fileContents.size() ; j++)
        {
            char[] chars = fileContents.get(j).toString().toCharArray();
            int currentLeftBlank = 0;
            for (int p = 0 ; p<chars.length;p++)
            {
                if (".".equals(chars[p]))
                {
                    currentLeftBlank++;
                }
                else
                {
                    break;
                }
            }
            if (leftBlank > currentLeftBlank)
            {
                leftBlank = currentLeftBlank;
            }
        }
        //2.2 删除左侧空列
        for (int q = 0 ; q < fileContents.size() ;q++)
        {
        	String line = fileContents.get(q).toString();
            //fileContents.Find(line).Value = line.Remove(0, leftBlank);
        	fileContents.set(q, line.substring(leftBlank + 1));
        }

        //2.3数出右侧空余数量
        int rightBlank = cols;
        for (int a = 0 ; a < fileContents.size() ; a++)
        {
        	String line = fileContents.get(a).toString();
            char[] chars = line.toCharArray();
            int currentRightBlank = 0;
            for (int i = chars.length - 1; i >= 0; i--)
            {
                if (".".equals(chars[i]))
                {
                    currentRightBlank++;
                }
                else
                {
                    break;
                }

            }
            if (rightBlank > currentRightBlank)
            {
                rightBlank = currentRightBlank;
            }
        }
        //2.4 删除右侧空列
        for (int b = 0 ; b < fileContents.size() ; b++)
        {
        	String line = fileContents.get(b).toString();
            fileContents.set(b, line.substring(0,line.length()-rightBlank));
        }
    }
    
    private String exportINK(Object tskMapFileNamesObj,String lot)
    {
        List tskMapFileNames = (List)tskMapFileNamesObj;
        //获取类文件所在的路径，为获取web应用路径做准备
        String classPath = this.getClass().getClassLoader().getResource("").getPath();
      	//获取模板路径与导出文件的路径
        String templatePath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/export/inkList/";
        String device = "";
        String fileName = FilenameHelper.generateDatetimeBasedFilename(lot,"");
        File zipFile = new File(templatePath+ "\\" + fileName + ".zip"); 
        Project prj = new Project();    
        Zip zip = new Zip();    
        zip.setProject(prj);    
        zip.setDestFile(zipFile);
        FileSet fileSet = new FileSet();
        fileSet.setProject(prj);  
        File folder = new File(templatePath);
		if (!folder.exists()) {
		    folder.mkdirs();
		}
        LinkedList suminfo = new LinkedList();
        for (int i = 0 ; i < tskMapFileNames.size() ; i++)
        {
            TSKInfoDTO tskinfo = binMapFileBean(String.valueOf(tskMapFileNames.get(i)));
            suminfo = HYN11_ExportInkList(tskinfo, templatePath + "\\" + tskinfo.getWafer_ID() + ".txt" ,suminfo);
            File srcdir = new File(templatePath);
            if (!srcdir.exists()){  
                throw new RuntimeException("文件夹不存在！");    
            }
            fileSet.setDir(srcdir);
            fileSet.setIncludes("**/" + tskinfo.getWafer_ID() + ".txt");
            device = tskinfo.getDevice_Name().split("-")[0];
        }
        ExportInkSummary(templatePath, device , suminfo);
        fileSet.setIncludes("**/" + hynfilename + ".HYN");
        zip.addFileset(fileSet);
        zip.execute();  
        return zipFile.getName();
    }

    @Override
	public InvokeResult get3360LotInfo(String customer, String testType, String device) {
		 FTPClient ftpClient = new FTPClient();
		 List fileLists = new ArrayList();
		 Map<String, Object> map = new HashMap<>();
		 List timeLists = new ArrayList();
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 boolean flag = true ;
		 try {
			
			ftpClient.connect(sumryServer);
			ftpClient.login(sumryFtpUserName, sumryFtpUserPwd);
			ftpClient.setDefaultTimeout(15000);
		    ftpClient.setSoTimeout(30000);
			ftpClient.setDataTimeout(15000);
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
			    throw new RuntimeException("连接FTP服务器失败!");
			}
            if(ftpClient.changeWorkingDirectory(customer + "/" + testType + "/" + device)){
            	FTPFile[] ftpFiles = ftpClient.listFiles();
            	FTPFile tempFile = new FTPFile();
            	for (int p = 0 ; p < ftpFiles.length ; p++) {
            		for (int q = 0 ; q < ftpFiles.length ; q++) {
            			int a = ftpFiles[p].getTimestamp().getTime().compareTo(ftpFiles[q].getTimestamp().getTime());
            			if ( a > 0 ) {
            				tempFile = ftpFiles[p];
            				ftpFiles[p] = ftpFiles[q];
            				ftpFiles[q] = tempFile;
            			}
            		}
            	}
    			for (int i = 0 ; i < ftpFiles.length ; i++) {
    				FTPFile file = ftpFiles[i];
    				if (file.isDirectory() && flag) {
    					if (file.getName().equals(device)) {
    						fileLists.clear();
							timeLists.clear();
                            flag = false;
						} else {
							fileLists.add(file.getName());
							Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							cal.setTime((Date)(file.getTimestamp().getTime()));
							timeLists.add(df.format(cal.getTime()));
						}
    				}
    			}
    			if (!flag) {
    				ftpClient.changeWorkingDirectory("/");
    				ftpClient.changeWorkingDirectory(customer + "/" + testType + "/" + device + "/" + device);
    				FTPFile[] ftpFiles2 = ftpClient.listFiles();
        			for (int i = 0 ; i < ftpFiles2.length ; i++) {
        				FTPFile file2 = ftpFiles2[i];
        				if (file2.isDirectory()) {
        					fileLists.add(file2.getName());
							Calendar cal = Calendar.getInstance(TimeZone.getDefault());
							cal.setTime((Date)(file2.getTimestamp().getTime()));
							timeLists.add(df.format(cal.getTime()));
        				}
        			}
    			}
    			map.put("directoryName", fileLists);
    			map.put("timeStamp", timeLists);
    			return InvokeResult.success(map);
            } else {
            	throw new RuntimeException("文件夹不存在，请确认！");
            }
		} catch (SocketException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return InvokeResult.failure(e.getMessage());
		}
	}

	@Override
	public void exportExcel3360(List<TestData3360InfoDTO> info3360s, String fileName) {
		// 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("3360信息情报");
        sheet.setDefaultColumnWidth(16);
        sheet.setDefaultRowHeightInPoints(20);
        // 第三步，在sheet中添加表头第0行t
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        
        if (info3360s.get(0).getDbin() !=null && info3360s.get(0).getMap() ==null) {
        	HSSFCell  cell = row.createCell(0);
            cell.setCellValue("Device Name");
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("Program Name");
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue("Revision");
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue("Tester Number");
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue("Prober Card");
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue("LotID");
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue("WID");
            cell.setCellStyle(style);
            cell = row.createCell(7);
            cell.setCellValue("TestStep");
            cell.setCellStyle(style);
            cell = row.createCell(8);
            cell.setCellValue("Operator");
            cell.setCellStyle(style);
            cell = row.createCell(9);
            cell.setCellValue("Temperature");
            cell.setCellStyle(style);
            cell = row.createCell(10);
            cell.setCellValue("Start Date");
            cell.setCellStyle(style);
            cell = row.createCell(11);
            cell.setCellValue("Start Time");
            cell.setCellStyle(style);
            cell = row.createCell(12);
            cell.setCellValue("End Date");
            cell.setCellStyle(style);
            cell = row.createCell(13);
            cell.setCellValue("End Time");
            cell.setCellStyle(style);
            cell = row.createCell(14);
            cell.setCellValue("Total Test Time");
            cell.setCellStyle(style);
            cell = row.createCell(15);
            cell.setCellValue("GrossDie");
            cell.setCellStyle(style);
            cell = row.createCell(16);
            cell.setCellValue("GoodDie");
            cell.setCellStyle(style);
            cell = row.createCell(17);
            cell.setCellValue("BadDie");
            cell.setCellStyle(style);
            cell = row.createCell(18);
            cell.setCellValue("Yield");
            cell.setCellStyle(style);
            for ( int a = 1 ; a <= info3360s.get(0).getMapSize() ; a++) {
            	 cell = row.createCell(18+a);
                 cell.setCellValue("DBIN" + String.valueOf(a));
                 cell.setCellStyle(style);
    		}

    	     // 第五步，写入实体数据 实际应用中这些数据从数据库得到，   
    	     for (int i = 0; i < info3360s.size(); i++)  
    	     {  
    	    	 TestData3360InfoDTO info3360DTO = (TestData3360InfoDTO) info3360s.get(i);
    	         row = sheet.createRow((int) i + 1);   
    	         HSSFCell datacell = row.createCell(0);
    	         datacell.setCellValue(info3360DTO.getDevice());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(1);
    	         datacell.setCellValue(info3360DTO.getProgramName()); 
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(2);
    	         datacell.setCellValue(info3360DTO.getRevision()); 
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(3);
                 datacell.setCellValue(info3360DTO.getTesterNumber());
                 datacell.setCellStyle(style);
    	         datacell = row.createCell(4);
    	         datacell.setCellValue(info3360DTO.getProberCard());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(5);
    	         datacell.setCellValue(info3360DTO.getLotID()); 
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(6);
    	         datacell.setCellValue(info3360DTO.getwID());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(7);
    	         datacell.setCellValue(info3360DTO.getTestStep());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(8);
    	         datacell.setCellValue(info3360DTO.getOperator());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(9);
                 datacell.setCellValue(info3360DTO.getTemperature());
                 datacell.setCellStyle(style);
    	         datacell = row.createCell(10);
    	         datacell.setCellValue(info3360DTO.getStartDate());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(11);
    	         datacell.setCellValue(info3360DTO.getStartTime());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(12);
    	         datacell.setCellValue(info3360DTO.getEndDate());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(13);
    	         datacell.setCellValue(info3360DTO.getEndTime());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(14);
    	         datacell.setCellValue(info3360DTO.getTotalTestTime());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(15);
    	         datacell.setCellValue(info3360DTO.getGrossDie());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(16);
    	         datacell.setCellValue(info3360DTO.getGoodDie());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(17);
    	         datacell.setCellValue(info3360DTO.getBadDie());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(18);
    	         datacell.setCellValue(info3360DTO.getYield());
    	         datacell.setCellStyle(style);
    	         for ( int b = 1 ; b <= info3360s.get(0).getMapSize() ; b++) {
             		if ( b < 10) {
             			datacell = row.createCell(18+b);
           	            datacell.setCellValue(String.valueOf(info3360s.get(i).getDbin().get("DBIN00"+String.valueOf(b))));
           	            datacell.setCellStyle(style);
             		} else if ( b >= 10 && b < 100) {
             			datacell = row.createCell(18+b);
           	            datacell.setCellValue(String.valueOf(info3360s.get(i).getDbin().get("DBIN0"+String.valueOf(b))));
           	            datacell.setCellStyle(style);
             		} else {
             			datacell = row.createCell(18+b);
           	            datacell.setCellValue(String.valueOf(info3360s.get(i).getDbin().get("DBIN"+String.valueOf(b))));
           	            datacell.setCellStyle(style);
             		}
         		}
    	     }
        } else {
        	HSSFCell  cell = row.createCell(0);
            cell.setCellValue("Wafer Summary Data");
            cell.setCellStyle(style);
            cell = row.createCell(1);
            cell.setCellValue("Handler/Prober Name");
            cell.setCellStyle(style);
            cell = row.createCell(2);
            cell.setCellValue("Testing time");
            cell.setCellStyle(style);
            cell = row.createCell(3);
            cell.setCellValue("Device Name");
            cell.setCellStyle(style);
            cell = row.createCell(4);
            cell.setCellValue("Lot ID");
            cell.setCellStyle(style);
            cell = row.createCell(5);
            cell.setCellValue("Wafer ID");
            cell.setCellStyle(style);
            cell = row.createCell(6);
            cell.setCellValue("Probe Card No");
            cell.setCellStyle(style);
            cell = row.createCell(7);
            cell.setCellValue("Yield");
            cell.setCellStyle(style);
            cell = row.createCell(8);
            cell.setCellValue("Pass");
            cell.setCellStyle(style);
            cell = row.createCell(9);
            cell.setCellValue("Fail");
            cell.setCellStyle(style);
            cell = row.createCell(10);
            cell.setCellValue("SW_PASS_CLASS2(2)");
            cell.setCellStyle(style);
            for ( int p = 0 ; p < ((List)info3360s.get(0).getMap().get("listTitle")).size() ; p++) {
            	 cell = row.createCell(11+p);
            	 String prop = String.valueOf(((List)info3360s.get(0).getMap().get("listTitle")).get(p));
                 cell.setCellValue(prop.substring(0,prop.lastIndexOf("-TestItem")));
                 cell.setCellStyle(style);
    		}

    	     // 第五步，写入实体数据 实际应用中这些数据从数据库得到，   
    	     for (int i = 0; i < info3360s.size(); i++)  
    	     {  
    	    	 TestData3360InfoDTO info3360DTO = (TestData3360InfoDTO) info3360s.get(i);
    	         row = sheet.createRow((int) i + 1);   
    	         HSSFCell datacell = row.createCell(0);
    	         datacell.setCellValue(info3360DTO.getWaferSummaryData());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(1);
    	         datacell.setCellValue(String.valueOf(info3360DTO.getMap().get("Handler/Prober Name"))); 
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(2);
    	         datacell.setCellValue(info3360DTO.getTestingDate()); 
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(3);
                 datacell.setCellValue(String.valueOf(info3360DTO.getMap().get("Device Name")));
                 datacell.setCellStyle(style);
    	         datacell = row.createCell(4);
    	         datacell.setCellValue(String.valueOf(info3360DTO.getMap().get("Lot ID")));
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(5);
    	         datacell.setCellValue(String.valueOf(info3360DTO.getMap().get("Wafer ID"))); 
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(6);
    	         datacell.setCellValue(String.valueOf(info3360DTO.getMap().get("Probe Card No")));
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(7);
    	         datacell.setCellValue(info3360DTO.getYield());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(8);
    	         datacell.setCellValue(info3360DTO.getPass());
    	         datacell.setCellStyle(style);
    	         datacell = row.createCell(9);
                 datacell.setCellValue(info3360DTO.getFail());
                 datacell.setCellStyle(style);
                 datacell = row.createCell(10);
                 datacell.setCellValue(info3360DTO.getSwPassClass2());
                 datacell.setCellStyle(style);
                 for ( int q = 0 ; q < ((List)info3360s.get(0).getMap().get("listValue")).size() ; q++) {
                	 cell = row.createCell(11+q);
                     cell.setCellValue(String.valueOf(((List)info3360s.get(i).getMap().get("listValue")).get(q)));
                     cell.setCellStyle(style);
        		}
    	     }
        }
        
	     // 第六步，将文件存到指定位置   
	     try  
	     {  
	 		 //获取类文件所在的路径，为获取web应用路径做准备
	 		 String classPath = this.getClass().getClassLoader().getResource("").getPath();
	 		 //获取模板路径与导出文件的路径
	 		 String templatePath = classPath.substring(0, classPath.indexOf("WEB-INF")) + "excel/";
	 		 String exportPath = templatePath + "export/3360/";
	 		 File folder = new File(exportPath);
	 		 if (!folder.exists()) {
				folder.mkdirs();
			 }
	         FileOutputStream fout = new FileOutputStream(exportPath + fileName);  
	         wb.write(fout);
	         fout.close();
	     }  
	     catch (IOException e)  
	     {  
	         e.printStackTrace();  
	     }  
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public String getFtpUserPwd() {
		return ftpUserPwd;
	}

	public void setFtpUserPwd(String ftpUserPwd) {
		this.ftpUserPwd = ftpUserPwd;
	}

	public String getSumryServer() {
		return sumryServer;
	}

	public void setSumryServer(String sumryServer) {
		this.sumryServer = sumryServer;
	}

	public String getSumryFtpUserName() {
		return sumryFtpUserName;
	}

	public void setSumryFtpUserName(String sumryFtpUserName) {
		this.sumryFtpUserName = sumryFtpUserName;
	}

	public String getSumryFtpUserPwd() {
		return sumryFtpUserPwd;
	}

	public void setSumryFtpUserPwd(String sumryFtpUserPwd) {
		this.sumryFtpUserPwd = sumryFtpUserPwd;
	}
}
