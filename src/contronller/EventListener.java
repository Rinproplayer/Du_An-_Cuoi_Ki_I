package contronller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.HoSoThueView;

public class EventListener implements ActionListener {

	private HoSoThueView hoSoThueView;

	public EventListener(HoSoThueView hoSoThueView) {
		this.setHoSoThueView(hoSoThueView);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String eventName = e.getActionCommand();
		switch (eventName) {
		case "Thêm": 
			hoSoThueView.addNew();
			break;
		case "Lưu": 
			hoSoThueView.addBDS();
			break;
		case "Xóa":
			hoSoThueView.deleteDBS();
			break;
		case "Cập Nhật":
			hoSoThueView.updateBDS();
			break;
		case "Tìm":
			hoSoThueView.searchBDS();
			break;
		default:

		}

	}

	public HoSoThueView getHoSoThueView() {
		return hoSoThueView;
	}

	public void setHoSoThueView(HoSoThueView hoSoThueView) {
		this.hoSoThueView = hoSoThueView;
	}

}