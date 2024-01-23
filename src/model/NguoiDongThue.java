package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.JDBCUtil;

public class NguoiDongThue {
	private int IDNguoiDongThue;
	private String HoTen;
	private String DiaChi;
	private int SoDienThoai;
	private Date NgaySinh;

	public NguoiDongThue(int iDNguoiDongThue, String hoTen, String diaChi, int soDienThoai, Date ngaySinh) {
		super();
		IDNguoiDongThue = iDNguoiDongThue;
		HoTen = hoTen;
		DiaChi = diaChi;
		SoDienThoai = soDienThoai;
		NgaySinh = ngaySinh;
	}

	public NguoiDongThue() {
		super();
	}

	public int getIDNguoiDongThue() {
		return IDNguoiDongThue;
	}

	public void setIDNguoiDongThue(int iDNguoiDongThue) {
		IDNguoiDongThue = iDNguoiDongThue;
	}

	public String getHoTen() {
		return HoTen;
	}

	public void setHoTen(String hoTen) {
		HoTen = hoTen;
	}

	public String getDiaChi() {
		return DiaChi;
	}

	public void setDiaChi(String diaChi) {
		DiaChi = diaChi;
	}

	public int getSoDienThoai() {
		return SoDienThoai;
	}

	public void setSoDienThoai(int soDienThoai) {
		SoDienThoai = soDienThoai;
	}

	public Date getNgaySinh() {
		return NgaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		NgaySinh = ngaySinh;
	}

	@Override
	public String toString() {
		return "nguoidongthue [IDNguoiDongThue=" + IDNguoiDongThue + ", HoTen=" + HoTen + ", DiaChi=" + DiaChi
				+ ", SoDienThoai=" + SoDienThoai + ", NgaySinh=" + NgaySinh + "]";
	}

	public static void update(NguoiDongThue ndt) {
	    Connection conn = JDBCUtil.getConnection();
	    PreparedStatement st;
	    try {
	        st = conn.prepareStatement("UPDATE nguoidongthue SET"
	                + " HoTen = ?,"
	                + " NgaySinh = ?,"
	                + " DiaChi = ?,"
	                + " SoDienThoai = ?"
	                + " WHERE IDNguoiDongThue = ?");
	        st.setString(1, ndt.getHoTen());
	        st.setDate(2, ndt.getNgaySinh());
	        st.setString(3, ndt.getDiaChi());
	        st.setFloat(4, ndt.getSoDienThoai());
	        st.setFloat(5, ndt.getIDNguoiDongThue());
	        System.out.println(st.toString());
	        st.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}


	public static void insert(NguoiDongThue ndt) {
	    Connection conn = JDBCUtil.getConnection();
	    PreparedStatement st;
	    try {
	        st = conn.prepareStatement("INSERT INTO `qltnd`.`nguoidongthue`"
	                + "(`IDNguoiDongThue`, `HoTen`, `NgaySinh`, `DiaChi`, `SoDienThoai`) "
	                + "VALUES (?, ?, ?, ?, ?)");
	        st.setFloat(1, ndt.getIDNguoiDongThue());
	        st.setString(2, ndt.getHoTen());
	        st.setDate(3, ndt.getNgaySinh());
	        st.setString(4, ndt.getDiaChi());
	        st.setFloat(5, ndt.getSoDienThoai());
	        System.out.println(st.toString());
	        st.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}

	
}
