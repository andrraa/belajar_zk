package com.sdd.latihan;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

import com.sdd.latihan.model.Employee;

public class MyViewModel {	
	
	private Employee employee;
	private List<Employee> listEmployee = new ArrayList<>();
	
	@Wire
	private Grid gridEmployee;

	@AfterCompose
	public void init(@ContextParam(ContextType.VIEW) Component view) {
	    Selectors.wireComponents(view, this, false);
	    employee = new Employee();
	    
	    gridEmployee.setRowRenderer(new RowRenderer<Employee>() {

		@Override
		public void render(Row row, Employee data, int index) throws Exception {
		    Label nomor = new Label();
		    nomor.setValue(String.valueOf(++index));
		    row.appendChild(nomor);
		    
		    Label nama = new Label();
		    nama.setValue(data.getNama());
		    row.appendChild(nama);
		    
		    Label alamat = new Label();
		    alamat.setValue(data.getAlamat());
		    row.appendChild(alamat);
		    
		    Button btndelete = new Button();
		    btndelete.setLabel("Delete");
		    btndelete.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
		        @Override
		        public void onEvent(Event event) throws Exception {
		            deleteEmployee(data);
		        }
		    });
		    row.appendChild(btndelete);
		}
	    });
	}

	@Command
	@NotifyChange("*")
	public void dosubmit() {
//	    Messagebox.show("Nama: " + employee.getNama() + "\n" + "Alamat: " + employee.getAlamat());
	    listEmployee.add(employee);
	    gridEmployee.setModel(new ListModelList<Employee>(listEmployee));
	    employee = new Employee();
	}
	
	public Employee getEmployee() {
	    return employee;
	}

	public void setEmployee(Employee employee) {
	    this.employee = employee;
	}
	
	public void deleteEmployee(Employee employee) {
	    listEmployee.remove(employee);
	    gridEmployee.setModel(new ListModelList<>(listEmployee));
	}
}
