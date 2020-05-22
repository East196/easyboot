package com.github.east196.core.poi;

public class XlsColumn {

	private String field;
	private String desc;

	public XlsColumn() {
	}

	public XlsColumn(String field, String desc) {
		this.field = field;
		this.desc = desc;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((desc == null) ? 0 : desc.hashCode());
		result = (prime * result) + ((field == null) ? 0 : field.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		XlsColumn other = (XlsColumn) obj;
		if (desc == null) {
			if (other.desc != null) {
				return false;
			}
		} else if (!desc.equals(other.desc)) {
			return false;
		}
		if (field == null) {
			if (other.field != null) {
				return false;
			}
		} else if (!field.equals(other.field)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "XlsColumn [field=" + field + ", desc=" + desc + "]";
	}

}
