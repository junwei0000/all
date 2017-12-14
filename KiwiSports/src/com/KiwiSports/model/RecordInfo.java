package com.KiwiSports.model;

public class RecordInfo {
	String record_id;
	String uid;
	String pos_name;
	String posid;
	String date_time;
	String distanceTraveled;
	long duration;
	String verticalDistance;

	String topSpeed;
	String dropTraveled;
	String nSteps;
	String matchSpeed;
	String maxMatchSpeed;
	String maxSlope;
	String maxAltitude;
	String currentAltitude;

	String averageMatchSpeed;
	String averageSpeed;
	long freezeDuration;
	String maxHoverDuration;
	String totalHoverDuration;
	String lapCount;
	String wrestlingCount;
	String cableCarQueuingDuration;
	String address;
	String minAltidue;
	String calorie;
	String sportsType;
	String latitudeOffset;
	String longitudeOffset;
	String upHillDistance;
	String downHillDistance;
	String extendedField1;
	String extendedField2;
	String extendedField3;
	String create_time;
	String cstatus;

	public RecordInfo(String record_id, String uid, String posid, String date_time, String distanceTraveled,
			long duration, String verticalDistance, String topSpeed, String dropTraveled, String nSteps,
			String matchSpeed, String maxMatchSpeed, String maxSlope, String maxAltitude, String currentAltitude,
			String averageMatchSpeed, String averageSpeed, long freezeDuration, String maxHoverDuration,
			String totalHoverDuration, String hopCount, String wrestlingCount, String cableCarQueuingDuration,
			String address, String minAltidue, String calorie, String sportsType, String latitudeOffset,
			String longitudeOffset, String upHillDistance, String downHillDistance, String extendedField1,
			String extendedField2, String extendedField3, String create_time, String cstatus,String pos_name) {
		super();
		this.record_id = record_id;
		this.uid = uid;
		this.posid = posid;
		this.date_time = date_time;
		this.distanceTraveled = distanceTraveled;
		this.duration = duration;
		this.verticalDistance = verticalDistance;
		this.topSpeed = topSpeed;
		this.dropTraveled = dropTraveled;
		this.nSteps = nSteps;
		this.matchSpeed = matchSpeed;
		this.maxMatchSpeed = maxMatchSpeed;
		this.maxSlope = maxSlope;
		this.maxAltitude = maxAltitude;
		this.currentAltitude = currentAltitude;
		this.averageMatchSpeed = averageMatchSpeed;
		this.averageSpeed = averageSpeed;
		this.freezeDuration = freezeDuration;
		this.maxHoverDuration = maxHoverDuration;
		this.totalHoverDuration = totalHoverDuration;
		this.lapCount = hopCount;
		this.wrestlingCount = wrestlingCount;
		this.cableCarQueuingDuration = cableCarQueuingDuration;
		this.address = address;
		this.minAltidue = minAltidue;
		this.calorie = calorie;
		this.sportsType = sportsType;
		this.latitudeOffset = latitudeOffset;
		this.longitudeOffset = longitudeOffset;
		this.upHillDistance = upHillDistance;
		this.downHillDistance = downHillDistance;
		this.extendedField1 = extendedField1;
		this.extendedField2 = extendedField2;
		this.extendedField3 = extendedField3;
		this.create_time = create_time;
		this.cstatus = cstatus;
		this.pos_name= pos_name;
	}

	 
	public String getPos_name() {
		return pos_name;
	}


	public void setPos_name(String pos_name) {
		this.pos_name = pos_name;
	}


	public String getRecord_id() {
		return record_id;
	}

	public void setRecord_id(String record_id) {
		this.record_id = record_id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPosid() {
		return posid;
	}

	public void setPosid(String posid) {
		this.posid = posid;
	}

	public String getDate_time() {
		return date_time;
	}

	public void setDate_time(String date_time) {
		this.date_time = date_time;
	}

	public String getDistanceTraveled() {
		return distanceTraveled;
	}

	public void setDistanceTraveled(String distanceTraveled) {
		this.distanceTraveled = distanceTraveled;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getVerticalDistance() {
		return verticalDistance;
	}

	public void setVerticalDistance(String verticalDistance) {
		this.verticalDistance = verticalDistance;
	}

	public String getTopSpeed() {
		return topSpeed;
	}

	public void setTopSpeed(String topSpeed) {
		this.topSpeed = topSpeed;
	}

	public String getDropTraveled() {
		return dropTraveled;
	}

	public void setDropTraveled(String dropTraveled) {
		this.dropTraveled = dropTraveled;
	}

	public String getnSteps() {
		return nSteps;
	}

	public void setnSteps(String nSteps) {
		this.nSteps = nSteps;
	}

	public String getMatchSpeed() {
		return matchSpeed;
	}

	public void setMatchSpeed(String matchSpeed) {
		this.matchSpeed = matchSpeed;
	}

	public String getMaxMatchSpeed() {
		return maxMatchSpeed;
	}

	public void setMaxMatchSpeed(String maxMatchSpeed) {
		this.maxMatchSpeed = maxMatchSpeed;
	}

	public String getMaxSlope() {
		return maxSlope;
	}

	public void setMaxSlope(String maxSlope) {
		this.maxSlope = maxSlope;
	}

	public String getMaxAltitude() {
		return maxAltitude;
	}

	public void setMaxAltitude(String maxAltitude) {
		this.maxAltitude = maxAltitude;
	}

	public String getCurrentAltitude() {
		return currentAltitude;
	}

	public void setCurrentAltitude(String currentAltitude) {
		this.currentAltitude = currentAltitude;
	}

	public String getAverageMatchSpeed() {
		return averageMatchSpeed;
	}

	public void setAverageMatchSpeed(String averageMatchSpeed) {
		this.averageMatchSpeed = averageMatchSpeed;
	}

	public String getAverageSpeed() {
		return averageSpeed;
	}

	public void setAverageSpeed(String averageSpeed) {
		this.averageSpeed = averageSpeed;
	}

	public long getFreezeDuration() {
		return freezeDuration;
	}

	public void setFreezeDuration(long freezeDuration) {
		this.freezeDuration = freezeDuration;
	}

	public String getMaxHoverDuration() {
		return maxHoverDuration;
	}

	public void setMaxHoverDuration(String maxHoverDuration) {
		this.maxHoverDuration = maxHoverDuration;
	}

	public String getTotalHoverDuration() {
		return totalHoverDuration;
	}

	public void setTotalHoverDuration(String totalHoverDuration) {
		this.totalHoverDuration = totalHoverDuration;
	}

	public String getLopCount() {
		return lapCount;
	}

	public void setLopCount(String lapCount) {
		this.lapCount = lapCount;
	}

	public String getWrestlingCount() {
		return wrestlingCount;
	}

	public void setWrestlingCount(String wrestlingCount) {
		this.wrestlingCount = wrestlingCount;
	}

	public String getCableCarQueuingDuration() {
		return cableCarQueuingDuration;
	}

	public void setCableCarQueuingDuration(String cableCarQueuingDuration) {
		this.cableCarQueuingDuration = cableCarQueuingDuration;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMinAltidue() {
		return minAltidue;
	}

	public void setMinAltidue(String minAltidue) {
		this.minAltidue = minAltidue;
	}

	public String getCalorie() {
		return calorie;
	}

	public void setCalorie(String calorie) {
		this.calorie = calorie;
	}

	public String getSportsType() {
		return sportsType;
	}

	public void setSportsType(String sportsType) {
		this.sportsType = sportsType;
	}

	public String getLatitudeOffset() {
		return latitudeOffset;
	}

	public void setLatitudeOffset(String latitudeOffset) {
		this.latitudeOffset = latitudeOffset;
	}

	public String getLongitudeOffset() {
		return longitudeOffset;
	}

	public void setLongitudeOffset(String longitudeOffset) {
		this.longitudeOffset = longitudeOffset;
	}

	public String getUpHillDistance() {
		return upHillDistance;
	}

	public void setUpHillDistance(String upHillDistance) {
		this.upHillDistance = upHillDistance;
	}

	public String getDownHillDistance() {
		return downHillDistance;
	}

	public void setDownHillDistance(String downHillDistance) {
		this.downHillDistance = downHillDistance;
	}

	public String getExtendedField1() {
		return extendedField1;
	}

	public void setExtendedField1(String extendedField1) {
		this.extendedField1 = extendedField1;
	}

	public String getExtendedField2() {
		return extendedField2;
	}

	public void setExtendedField2(String extendedField2) {
		this.extendedField2 = extendedField2;
	}

	public String getExtendedField3() {
		return extendedField3;
	}

	public void setExtendedField3(String extendedField3) {
		this.extendedField3 = extendedField3;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getCstatus() {
		return cstatus;
	}

	public void setCstatus(String cstatus) {
		this.cstatus = cstatus;
	}

}
