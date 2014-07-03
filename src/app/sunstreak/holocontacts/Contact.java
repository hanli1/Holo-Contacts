package app.sunstreak.holocontacts;

import android.content.res.Resources;
import android.graphics.Bitmap;

public class Contact {
	private String name;
	private String phone;
	private String id;
	private Bitmap pic;
	
	public Contact(String name, String phone)
	{
		this.name = name;
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Bitmap getPic() {
		return pic;
	}
	public void setPic(Bitmap pic) {
		this.pic = pic;
	}
}
