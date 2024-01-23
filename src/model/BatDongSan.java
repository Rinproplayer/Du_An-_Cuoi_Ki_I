package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.JDBCUtil;

public class BatDongSan {

	private int IDBatDongSan;
	private int IDNguoiSoHuu;
	private String LoaiBDS;
	private String ViTri;
	private float DienTich;
	private long GiaTri;
	private long TienThue;
	private boolean TrangThaiThanhToan;
	private NguoiDongThue nguoiDongThue;

	public BatDongSan() {
		super();
	}

	public BatDongSan(int iDBatDongSan, int iDNguoiSoHuu, String loaiBDS, String viTri,

			float dienTich, long giaTri, long tienThue, boolean trangThaiThanhToan, NguoiDongThue nguoiDongThue) {
		super();
		IDBatDongSan = iDBatDongSan;
		IDNguoiSoHuu = iDNguoiSoHuu;
		LoaiBDS = loaiBDS;
		ViTri = viTri;
		DienTich = dienTich;
		GiaTri = giaTri;
		TienThue = tienThue;
		TrangThaiThanhToan = trangThaiThanhToan;
		this.nguoiDongThue = nguoiDongThue;
	}

	public NguoiDongThue getNguoiDongThue() {
		return nguoiDongThue;
	}

	public void setNguoiDongThue(NguoiDongThue nguoiDongThue) {
		this.nguoiDongThue = nguoiDongThue;
	}

	public int getIDBatDongSan() {
		return IDBatDongSan;
	}

	public void setIDBatDongSan(int iDBatDongSan) {
		IDBatDongSan = iDBatDongSan;
	}

	public int getIDNguoiSoHuu() {
		return IDNguoiSoHuu;
	}

	public void setIDNguoiSoHuu(int iDNguoiSoHuu) {
		IDNguoiSoHuu = iDNguoiSoHuu;
	}

	public String getLoaiBDS() {
		return LoaiBDS;
	}

	public void setLoaiBDS(String loaiBDS) {
		LoaiBDS = loaiBDS;
	}

	public float getDienTich() {
		return DienTich;
	}

	public void setDienTich(float dienTich) {
		DienTich = dienTich;
	}

	public long getGiaTri() {
		return GiaTri;
	}

	public void setGiaTri(long giaTri) {
		GiaTri = giaTri;
	}

	public long getTienThue() {
		return TienThue;
	}

	public void setTienThue(long tienThue) {
		TienThue = tienThue;
	}

	public boolean isTrangThaiThanhToan() {
		return TrangThaiThanhToan;
	}

	public void setTrangThaiThanhToan(boolean trangThaiThanhToan) {
		TrangThaiThanhToan = trangThaiThanhToan;
	}

	public boolean getTrangThaiThanhToan() {
		return this.TrangThaiThanhToan;
	}

	public String getViTri() {
		return ViTri;
	}

	public void setViTri(String viTri) {
		ViTri = viTri;
	}

	@Override
	public String toString() {
		return "BatDongSan [IDBatDongSan=" + IDBatDongSan + ", IDNguoiSoHuu=" + IDNguoiSoHuu + ", LoaiBDS=" + LoaiBDS
				+ ", ViTri=" + ViTri + ", DienTich=" + DienTich + ", GiaTri=" + GiaTri + ", TienThue=" + TienThue
				+ ", TrangThaiThanhToan=" + TrangThaiThanhToan + "]";
	}

	public static void insert(BatDongSan bds) {
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement st;
		try {
			st = conn.prepareStatement("INSERT INTO `qltnd`.`batdongsan` "
					+ "(`IDBatDongSan`, `IDChuSoHuu`, `LoaiBDS`, `ViTri`, `DienTich`, `GiaTri`, `TienThue`, `TrangThaiThanhToan`) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
			st.setInt(1, bds.getIDBatDongSan());
			st.setInt(2, bds.getIDNguoiSoHuu());
			st.setString(3, bds.getLoaiBDS());
			st.setString(4, bds.getViTri());
			st.setFloat(5, bds.getDienTich());
			st.setDouble(6, bds.getGiaTri());
			st.setDouble(7, bds.getTienThue());
			st.setBoolean(8, bds.getTrangThaiThanhToan());
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

	public static void update(BatDongSan bds) {
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement st;
		try {
			st = conn.prepareStatement("UPDATE batdongsan SET \r\n" + " IDChuSoHuu= ?, \r\n" + " LoaiBDS = ?, \r\n"
					+ " ViTri = ? ,\r\n" + " DienTich = ?, \r\n" + "	GiaTri = ?, \r\n" + "	TienThue = ?, \r\n"
					+ "	TrangThaiThanhToan = ? \r\n" + "	WHERE  IDBatDongSan = ?");
			st.setInt(1, bds.getIDNguoiSoHuu());
			st.setString(2, bds.getLoaiBDS());
			st.setString(3, bds.getViTri());
			st.setFloat(4, bds.getDienTich());
			st.setDouble(5, bds.getGiaTri());
			st.setDouble(6, bds.getTienThue());
			st.setBoolean(7, bds.getTrangThaiThanhToan());
			st.setInt(8, bds.getIDBatDongSan());
			System.out.println(st.toString());
			NguoiDongThue.update(bds.getNguoiDongThue());
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

	public static void delete(int idBDS) {
		Connection conn = JDBCUtil.getConnection();
		PreparedStatement st;
		try {
			st = conn.prepareStatement("DELETE FROM batdongsan WHERE IDBatDongSan = ?");
			st.setInt(1, idBDS);
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
