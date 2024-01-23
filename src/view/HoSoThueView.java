package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import contronller.EventListener;
import database.JDBCUtil;
import model.BatDongSan;
import model.HoSoThueModel;
import model.NguoiDongThue;

public class HoSoThueView extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int NUMBER_OF_COLUMN = 12;
	private static final int ID_BDS_INDEX = 0;
	private static final int ID_NSH_INDEX = 1;
	private static final int HO_TEN_INDEX = 2;
	private static final int NGAY_SINH_INDEX = 3;
	private static final int DIA_CHI_INDEX = 4;
	private static final int SO_DIEN_THOAI_INDEX = 5;
	private static final int LOAI_BDS_INDEX = 6;
	private static final int VI_TRI_INDEX = 7;
	private static final int DIEN_TICH_INDEX = 8;
	private static final int GIA_TRI_INDEX = 9;
	private static final int TIEN_THUE_INDEX = 10;
	private static final int TRANG_THAI_INDEX = 11;
	private static final float TAX_PERCENT = 0.05f;

	private JPanel contentPane;
	HoSoThueModel model;
	private JTextField searchBDSValue;
	private JTable table;
	private JTextField idBDS;
	private JTextField idCSH;
	private JTextField hoVaTen;
	private JTextField ngaySinh;
	private JTextField diaChi;
	private JTextField soDienThoai;
	private JTextField loaiBDS;
	private JTextField viTri;
	private JTextField dienTich;
	private JFormattedTextField giaTri;
	private JFormattedTextField tienThue;
	private JComboBox<String> trangThaiThanhToan;

	private static final String[] TBL_HEADER = { "ID BDS", "ID CSH", "H\u1ECD v\u00E0 T\u00EAn", "Ng\u00E0y Sinh",
			"\u0110\u1ECBa Ch\u1EC9", "S\u1ED1 \u0110i\u1EC7n Tho\u1EA1i", "LoaiB\u0110S", "V\u1ECB Tr\u00ED",
			"Di\u1EC7n T\u00EDch", "Gi\u00E1 Tr\u1ECB", "Ti\u1EC1n Thu\u1EBF", "Tr\u1EA1ng Th\u00E1i Thanh To\u00E1n" };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HoSoThueView frame = new HoSoThueView();
					frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private List<BatDongSan> getListData(Integer idBDS) {

		Connection connection = JDBCUtil.getConnection();

		List<BatDongSan> bdsList = new ArrayList<BatDongSan>();
		String query = "Select * from batdongsan";
		if (idBDS != null) {
			query += " WHERE IDBatDongSan = ?";
		}
		try {
			PreparedStatement st = connection.prepareStatement(query);
			if (idBDS != null) {
				st.setInt(1, idBDS);
			}
			ResultSet rs = st.executeQuery();
			BatDongSan bds;
			while (rs.next()) {
				NguoiDongThue ndt = getNguoiDongThue(rs.getInt("IDChuSoHuu"));
				bds = new BatDongSan(rs.getInt("IDBatDongSan"), rs.getInt("IDChuSoHuu"), rs.getString("LoaiBDS"),
						rs.getString("ViTri"), rs.getFloat("DienTich"), rs.getLong("GiaTri"), rs.getLong("TienThue"),
						rs.getBoolean("TrangThaiThanhToan"), ndt);
				bdsList.add(bds);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bdsList;
	}

	private NguoiDongThue getNguoiDongThue(int idChuSoHuu) {
		Connection connection = JDBCUtil.getConnection();
		NguoiDongThue ndt = null;

		try {
			PreparedStatement st = connection
					.prepareStatement("SELECT * FROM nguoidongthue WHERE nguoidongthue.IDNguoiDongThue = ?");
			st.setInt(1, idChuSoHuu);

			ResultSet rs = st.executeQuery();
			if (rs.next()) {
				ndt = new NguoiDongThue(rs.getInt("IDNguoiDongThue"), rs.getString("HoTen"), rs.getString("DiaChi"),
						rs.getInt("SoDienThoai"), rs.getDate("NgaySinh"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ndt;

	}

	private void loadData(Integer idBDS) {
		List<BatDongSan> bdsList = getListData(idBDS);
		DefaultTableModel dtm = new DefaultTableModel(TBL_HEADER, 0);

		BatDongSan bds;
		for (int i = 0; i < bdsList.size(); i++) {

			Object[] obj = new Object[NUMBER_OF_COLUMN];
			bds = bdsList.get(i);
			obj[ID_BDS_INDEX] = bds.getIDBatDongSan();
			obj[ID_NSH_INDEX] = bds.getIDNguoiSoHuu();
			obj[HO_TEN_INDEX] = bds.getNguoiDongThue().getHoTen();
			obj[NGAY_SINH_INDEX] = bds.getNguoiDongThue().getNgaySinh();
			obj[DIA_CHI_INDEX] = bds.getNguoiDongThue().getDiaChi();
			obj[SO_DIEN_THOAI_INDEX] = bds.getNguoiDongThue().getSoDienThoai();
			obj[LOAI_BDS_INDEX] = bds.getLoaiBDS();
			obj[VI_TRI_INDEX] = bds.getViTri();
			obj[DIEN_TICH_INDEX] = bds.getDienTich();
			obj[GIA_TRI_INDEX] = bds.getGiaTri();
			obj[TIEN_THUE_INDEX] = bds.getTienThue();
			obj[TRANG_THAI_INDEX] = bds.getTrangThaiThanhToan() ? "Yes" : "No";

			dtm.addRow(obj);
		}

		table.setModel(dtm);
	}

	private JScrollPane createPanel() {
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 18));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		DefaultTableModel dtm = new DefaultTableModel(TBL_HEADER, 0);

		table.setModel(dtm);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(89);
		table.getColumnModel().getColumn(2).setPreferredWidth(120);
		table.getColumnModel().getColumn(6).setPreferredWidth(105);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRow = table.getSelectedRow();
				setDataIntoForm(selectedRow);
			}
		});

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 96, 1057, 183);
		return scrollPane;
	}

	private void setDataIntoForm(int selectedRow) {
		if (selectedRow >= 0) {
			idBDS.setText(String.valueOf(table.getValueAt(selectedRow, ID_BDS_INDEX)));
			idCSH.setText(String.valueOf(table.getValueAt(selectedRow, ID_NSH_INDEX)));
			hoVaTen.setText(String.valueOf(table.getValueAt(selectedRow, HO_TEN_INDEX)));
			ngaySinh.setText(String.valueOf(table.getValueAt(selectedRow, NGAY_SINH_INDEX)));
			diaChi.setText(String.valueOf(table.getValueAt(selectedRow, DIA_CHI_INDEX)));
			soDienThoai.setText(String.valueOf(table.getValueAt(selectedRow, SO_DIEN_THOAI_INDEX)));
			loaiBDS.setText(String.valueOf(table.getValueAt(selectedRow, LOAI_BDS_INDEX)));
			viTri.setText(String.valueOf(table.getValueAt(selectedRow, VI_TRI_INDEX)));
			dienTich.setText(String.valueOf(table.getValueAt(selectedRow, DIEN_TICH_INDEX)));
			giaTri.setValue((Long) table.getValueAt(selectedRow, GIA_TRI_INDEX));
			tienThue.setValue(table.getValueAt(selectedRow, TIEN_THUE_INDEX));
			trangThaiThanhToan.setSelectedItem(String.valueOf(table.getValueAt(selectedRow, TRANG_THAI_INDEX)));
		}
	}

	/**
	 * Create the frame.
	 * 
	 * @throws ParseException
	 */
	@SuppressWarnings("deprecation")
	public HoSoThueView() throws ParseException {
		EventListener listener = new EventListener(this);
		this.model = new HoSoThueModel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1092, 829);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel Label_loaibds_1 = new JLabel("ID BĐS");
		Label_loaibds_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Label_loaibds_1.setBounds(25, 10, 94, 42);
		contentPane.add(Label_loaibds_1);

		searchBDSValue = new JTextField();
		searchBDSValue.setFont(new Font("Tahoma", Font.PLAIN, 18));
		searchBDSValue.setColumns(10);
		searchBDSValue.setBounds(149, 10, 215, 42);
		contentPane.add(searchBDSValue);

		JButton btnNewButton = new JButton("Tìm");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton.setBounds(411, 10, 122, 42);
		contentPane.add(btnNewButton);
		btnNewButton.addActionListener(listener);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 62, 1057, 2);
		contentPane.add(separator);

		JLabel Label_loaibds_2 = new JLabel("Hồ Sơ Thuế");
		Label_loaibds_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Label_loaibds_2.setBounds(10, 62, 163, 42);
		contentPane.add(Label_loaibds_2);

		contentPane.add(createPanel());
		loadData(null);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(10, 303, 1057, 2);
		contentPane.add(separator_1);

		JLabel Label_loaibds_2_1 = new JLabel("ID BDS");
		Label_loaibds_2_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		Label_loaibds_2_1.setBounds(29, 359, 94, 42);
		contentPane.add(Label_loaibds_2_1);

		JLabel lblIdCsh = new JLabel("ID CSH");
		lblIdCsh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblIdCsh.setBounds(25, 405, 88, 42);
		contentPane.add(lblIdCsh);

		JLabel lblHoten = new JLabel("Họ và Tên");
		lblHoten.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblHoten.setBounds(29, 455, 163, 42);
		contentPane.add(lblHoten);

		JLabel lblNgaysinh = new JLabel("Ngày Sinh");
		lblNgaysinh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNgaysinh.setBounds(25, 505, 163, 42);
		contentPane.add(lblNgaysinh);

		idBDS = new JTextField();
		idBDS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idBDS.setColumns(10);
		idBDS.setBounds(118, 365, 163, 28);
		contentPane.add(idBDS);

		JLabel lblLoiBs = new JLabel("Loại BĐS");
		lblLoiBs.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLoiBs.setBounds(455, 359, 94, 42);
		contentPane.add(lblLoiBs);

		JLabel lblVTr = new JLabel("Vị Trí");
		lblVTr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblVTr.setBounds(455, 405, 94, 42);
		contentPane.add(lblVTr);

		JLabel lblDinTch = new JLabel("Diện Tích");
		lblDinTch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDinTch.setBounds(455, 455, 94, 42);
		contentPane.add(lblDinTch);

		JLabel lblGiTr = new JLabel("Giá Trị");
		lblGiTr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGiTr.setBounds(455, 505, 94, 42);
		contentPane.add(lblGiTr);

		JLabel lblNgaysinh_1 = new JLabel("Địa Chỉ");
		lblNgaysinh_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNgaysinh_1.setBounds(29, 562, 163, 42);
		contentPane.add(lblNgaysinh_1);

		JLabel lblTinThu = new JLabel("Tiền Thuế");
		lblTinThu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTinThu.setBounds(455, 557, 94, 42);
		contentPane.add(lblTinThu);

		JLabel lblSinThoi = new JLabel("SĐT");
		lblSinThoi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSinThoi.setBounds(25, 614, 94, 42);
		contentPane.add(lblSinThoi);

		JLabel lblTttt = new JLabel("TTTT");
		lblTttt.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTttt.setBounds(455, 614, 94, 42);
		contentPane.add(lblTttt);

		JLabel Label_loaibds_2_2 = new JLabel("Hồ Sơ Thuế");
		Label_loaibds_2_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		Label_loaibds_2_2.setBounds(25, 315, 163, 42);
		contentPane.add(Label_loaibds_2_2);

		idCSH = new JTextField();
		idCSH.setFont(new Font("Tahoma", Font.PLAIN, 18));
		idCSH.setColumns(10);
		idCSH.setBounds(118, 411, 163, 28);
		contentPane.add(idCSH);

		hoVaTen = new JTextField();
		hoVaTen.setFont(new Font("Tahoma", Font.PLAIN, 18));
		hoVaTen.setColumns(10);
		hoVaTen.setBounds(118, 461, 163, 28);
		contentPane.add(hoVaTen);

		ngaySinh = new JTextField();
		ngaySinh.setFont(new Font("Tahoma", Font.PLAIN, 18));
		ngaySinh.setColumns(10);
		ngaySinh.setBounds(118, 511, 163, 28);
		contentPane.add(ngaySinh);

		diaChi = new JTextField();
		diaChi.setFont(new Font("Tahoma", Font.PLAIN, 18));
		diaChi.setColumns(10);
		diaChi.setBounds(118, 568, 163, 28);
		contentPane.add(diaChi);

		soDienThoai = new JTextField();
		soDienThoai.setFont(new Font("Tahoma", Font.PLAIN, 18));
		soDienThoai.setColumns(10);
		soDienThoai.setBounds(118, 619, 163, 28);
		contentPane.add(soDienThoai);

		loaiBDS = new JTextField();
		loaiBDS.setFont(new Font("Tahoma", Font.PLAIN, 18));
		loaiBDS.setColumns(10);
		loaiBDS.setBounds(559, 365, 163, 28);
		contentPane.add(loaiBDS);

		viTri = new JTextField();
		viTri.setFont(new Font("Tahoma", Font.PLAIN, 18));
		viTri.setColumns(10);
		viTri.setBounds(559, 411, 163, 28);
		contentPane.add(viTri);

		dienTich = new JTextField();
		dienTich.setFont(new Font("Tahoma", Font.PLAIN, 18));
		dienTich.setColumns(10);
		dienTich.setBounds(559, 461, 163, 28);
		contentPane.add(dienTich);

		NumberFormat numFormat = new DecimalFormat("###,###");
		NumberFormatter numFormatter = new NumberFormatter(numFormat);

		giaTri = new JFormattedTextField(numFormatter);
		giaTri.setFont(new Font("Tahoma", Font.PLAIN, 18));
		giaTri.setColumns(10);
		giaTri.setBounds(559, 511, 163, 28);
		contentPane.add(giaTri);

		tienThue = new JFormattedTextField(numFormat);
		tienThue.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tienThue.setColumns(10);
		tienThue.disable();
		tienThue.setBounds(559, 568, 163, 28);
		contentPane.add(tienThue);

		trangThaiThanhToan = new JComboBox<String>();
		trangThaiThanhToan.setModel(new DefaultComboBoxModel<String>(new String[] { "Yes", "No" }));
		trangThaiThanhToan.setBounds(559, 627, 163, 28);
		contentPane.add(trangThaiThanhToan);
		this.setVisible(true);

		JButton btnThm = new JButton("Thêm");
		btnThm.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnThm.setBounds(39, 674, 122, 42);
		contentPane.add(btnThm);
		btnThm.addActionListener(listener);

		JButton btnXa = new JButton("Xóa");
		btnXa.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnXa.setBounds(242, 674, 122, 42);
		contentPane.add(btnXa);
		btnXa.addActionListener(listener);

		JButton btnCpNht = new JButton("Cập Nhật");
		btnCpNht.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnCpNht.setBounds(438, 674, 122, 42);
		contentPane.add(btnCpNht);
		btnCpNht.addActionListener(listener);

		JButton btnLu = new JButton("Lưu");
		btnLu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnLu.setBounds(641, 674, 122, 42);
		contentPane.add(btnLu);
		btnLu.addActionListener(listener);

		JSeparator separator_1_1 = new JSeparator();
		separator_1_1.setBounds(10, 662, 1057, 2);
		contentPane.add(separator_1_1);

	}

	public void addNew() {
		resetForm();
	}

	private void resetForm() {
		idBDS.setText("");
		idCSH.setText("");
		hoVaTen.setText("");
		ngaySinh.setText("");
		diaChi.setText("");
		soDienThoai.setText("");
		loaiBDS.setText("");
		viTri.setText("");
		dienTich.setText("");
		giaTri.setValue(0);
		tienThue.setValue(0);
		trangThaiThanhToan.setSelectedItem("No");
	}

	public void addBDS() {
		BatDongSan bds = new BatDongSan();
		NguoiDongThue ndt = new NguoiDongThue();
		long giaTriBDS = (Long) giaTri.getValue();
		bds.setIDBatDongSan(Integer.valueOf(idBDS.getText()));
		bds.setIDNguoiSoHuu(Integer.valueOf(idCSH.getText()));
		bds.setDienTich(Float.valueOf(dienTich.getText()));
		bds.setGiaTri(giaTriBDS);
		bds.setLoaiBDS(loaiBDS.getText());
		bds.setTienThue((long) (giaTriBDS * TAX_PERCENT));
		// TODO update
		boolean tttt = String.valueOf(trangThaiThanhToan.getSelectedIndex()).equals("yes") ? true : false;
		bds.setTrangThaiThanhToan(tttt);
		bds.setViTri(viTri.getText());
		
		
		ndt.setIDNguoiDongThue(Integer.valueOf(idCSH.getText()));
		ndt.setHoTen(hoVaTen.getText());
		ndt.setNgaySinh(Date.valueOf(ngaySinh.getText()));
		ndt.setDiaChi(diaChi.getText());
		ndt.setSoDienThoai(Integer.valueOf(soDienThoai.getText()));
		bds.setNguoiDongThue(ndt);
        NguoiDongThue.insert(ndt);
		BatDongSan.insert(bds);

		loadData(null);
	}

	public void updateBDS() {
		BatDongSan bds = new BatDongSan();
		NguoiDongThue ndt = new NguoiDongThue();
		bds.setIDBatDongSan(Integer.valueOf(idBDS.getText()));
		bds.setIDNguoiSoHuu(Integer.valueOf(idCSH.getText()));
		bds.setLoaiBDS(loaiBDS.getText());
		bds.setViTri(viTri.getText());
		bds.setDienTich(Float.valueOf(dienTich.getText()));
		bds.setGiaTri((long) giaTri.getValue());
		bds.setTienThue((long) ((long) giaTri.getValue() * TAX_PERCENT));
		boolean tttt = String.valueOf(trangThaiThanhToan.getSelectedItem()).equals("Yes") ? true : false;
		bds.setTrangThaiThanhToan(tttt);

		ndt.setIDNguoiDongThue(Integer.valueOf(idCSH.getText()));
		ndt.setHoTen(hoVaTen.getText());
		ndt.setNgaySinh(Date.valueOf(ngaySinh.getText()));
		ndt.setDiaChi(diaChi.getText());
		ndt.setSoDienThoai(Integer.valueOf(soDienThoai.getText()));
		bds.setNguoiDongThue(ndt);
		NguoiDongThue.update(ndt);
		BatDongSan.update(bds);
		loadData(null);
		resetForm();
		JOptionPane.showMessageDialog(contentPane, "Cập nhật thành công !");

	}

	public void deleteDBS() {
		int selectedRow = table.getSelectedRow();
		int idBDS = (int) table.getValueAt(selectedRow, ID_BDS_INDEX);
		BatDongSan.delete(idBDS);
		loadData(null);

	}

	public void searchBDS() {
		try {
			String idBDS = searchBDSValue.getText();
			if (!"".equals(idBDS)) {
				loadData(Integer.valueOf(idBDS));
			} else {
				loadData(null);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(contentPane, "Vui lòng nhập số");
		}
	}

}
