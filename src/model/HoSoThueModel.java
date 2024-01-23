package model;

import java.util.ArrayList;

public class HoSoThueModel {
private ArrayList<BatDongSan> dsBatDongSan;

public HoSoThueModel() {
	this.dsBatDongSan = new ArrayList<BatDongSan>();
}

public HoSoThueModel(ArrayList<BatDongSan> dsBatDongSan) {

	this.dsBatDongSan = dsBatDongSan;
}

public ArrayList<BatDongSan> getDsBatDongSan() {
	return dsBatDongSan;
}

public void setDsBatDongSan(ArrayList<BatDongSan> dsBatDongSan) {
	this.dsBatDongSan = dsBatDongSan;
}
public void insert(BatDongSan batDongSan) {
	this.dsBatDongSan.add(batDongSan);
}
public void delete(BatDongSan batDongSan) {
	this.dsBatDongSan.remove(batDongSan);
}
public void update(BatDongSan batDongSan) {
	this.dsBatDongSan.remove(batDongSan);
	this.dsBatDongSan.add(batDongSan);
}
}
