package com.mualab.org.biz.module.booking.listner;

import com.mualab.org.biz.model.booking.Bookings;

;

public interface StaffSelectionListener {

	void onStaffSelect(int position, Bookings bookingInfo);

}