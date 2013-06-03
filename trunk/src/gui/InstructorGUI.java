package gui;

import controller.PersonCtr;
import model.Instructor;
import model.LinkedList;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import swing2swt.layout.FlowLayout;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.layout.GridData;

public class InstructorGUI extends Composite {

	private PersonCtr persCtr;

	// Tables
	private Table table;

	// Text Fields
	private Text txt_id;
	private Text txt_firstName;
	private Text txt_surName;
	private Text txt_email;
	private Text txt_address;
	private Text txt_phoneNo;
	private Text search_name;

	private Button btn_delete;
	private Button btn_save;
	private Button btn_edit;
	private Button btn_create;
	private Text txt_salary;
	private Text txt_skills;

	public InstructorGUI(Composite parent, int style) {
		super(parent, style);
		this.setLayout(new BorderLayout(0, 0));

		persCtr = new PersonCtr();

		Composite composite_2 = new Composite(this, SWT.NONE);
		composite_2.setLayoutData(BorderLayout.WEST);
		composite_2.setLayout(new BorderLayout(0, 0));

		Composite composite_8 = new Composite(composite_2, SWT.NONE);
		composite_8.setLayoutData(BorderLayout.NORTH);
		RowLayout rl_composite_8 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_8.center = true;
		composite_8.setLayout(rl_composite_8);

		Label lbl_name = new Label(composite_8, SWT.NONE);
		lbl_name.setText("Name:");

		search_name = new Text(composite_8, SWT.BORDER);
		search_name.setLayoutData(new RowData(116, SWT.DEFAULT));

		Button btn_search = new Button(composite_8, SWT.NONE);
		btn_search.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = search_name.getText();
				showSearchedInstructors(name);
			}
		});
		btn_search.setText("Search");

		Composite composite_9 = new Composite(composite_2, SWT.NONE);
		composite_9.setLayout(new FillLayout(SWT.HORIZONTAL));

		ScrolledComposite scrolledComposite = new ScrolledComposite(
				composite_9, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		table = new Table(scrolledComposite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tblclmnId = new TableColumn(table, SWT.NONE);
		tblclmnId.setWidth(40);
		tblclmnId.setText("ID");

		TableColumn tblclmnFirstName = new TableColumn(table, SWT.NONE);
		tblclmnFirstName.setWidth(100);
		tblclmnFirstName.setText("FirstName");

		TableColumn tblclmnSurName = new TableColumn(table, SWT.NONE);
		tblclmnSurName.setWidth(100);
		tblclmnSurName.setText("SurName");
		scrolledComposite.setContent(table);
		scrolledComposite.setMinSize(table
				.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		Composite composite_3 = new Composite(this, SWT.NONE);
		composite_3.setLayout(new BorderLayout(0, 0));

		Composite composite_4 = new Composite(composite_3, SWT.NONE);
		composite_4.setLayoutData(BorderLayout.SOUTH);
		composite_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Composite composite_5 = new Composite(composite_4, SWT.NONE);
		composite_5.setLayout(new RowLayout(SWT.HORIZONTAL));

		btn_delete = new Button(composite_5, SWT.CENTER);
		btn_save = new Button(composite_5, SWT.CENTER);
		btn_save.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				boolean create = false;
				int instructorId = 0;
				String firstName = null;
				String surName = null;
				String address = null;
				String email = null;
				String phoneNo = null;
				double salary = 0.0;
				String skills = null;
				try {
					instructorId = Integer.parseInt(txt_id.getText());
				} catch (NumberFormatException ex) {
					create = true;
					boolean error = false;
					try {
						firstName = txt_firstName.getText();
						surName = txt_surName.getText();
						address = txt_address.getText();
						email = txt_email.getText();
						phoneNo = txt_phoneNo.getText();
						salary = Double.parseDouble(txt_salary.getText());
						skills = txt_skills.getText();
					} catch (Exception exc) {
						MessageBox box = new MessageBox(getShell(), 0);
						box.setText("Error");
						box.setMessage("There was an error. Please try again");
						box.open();
						error = true;
					}
					if (!error) {
						boolean ok = true;
						try {
							instructorId = persCtr.insertInstructor(firstName,
									surName, address, phoneNo, email, salary,
									"Instructor", skills);
						} catch (Exception ex1) {
							ok = false;
							MessageBox box = new MessageBox(getShell(), 0);
							box.setText("Error");
							box.setMessage("There was an error. Please try again");
							box.open();
						}
						if (ok) {
							showAllInstructors();
							showInstructor(instructorId);
						}
					}
				}
				if (!create) {
					boolean error = false;
					try {
						firstName = txt_firstName.getText();
						surName = txt_surName.getText();
						address = txt_address.getText();
						email = txt_email.getText();
						phoneNo = txt_phoneNo.getText();
						salary = Double.parseDouble(txt_salary.getText());
						skills = txt_skills.getText();
					} catch (Exception exc) {
						MessageBox box = new MessageBox(getShell(), 0);
						box.setText("Error");
						box.setMessage("There was an error. Please try again");
						box.open();
						error = true;
					}
					if (!error) {
						boolean ok = true;
						try {
							persCtr.updateInstructor(instructorId, firstName,
									surName, address, phoneNo, email, salary,
									"Instructor", skills);
						} catch (Exception ex1) {
							MessageBox box = new MessageBox(getShell(), 0);
							box.setText("Error");
							box.setMessage("There was an error. Please try again");
							box.open();
							ok = false;
						}
						if (ok) {
							showAllInstructors();
							showInstructor(instructorId);
						}
					}

				}
			}
		});
		btn_edit = new Button(composite_5, SWT.CENTER);
		btn_create = new Button(composite_5, SWT.CENTER);

		btn_delete.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int instructorId = 0;
				boolean error = false;
				try {
					instructorId = Integer.parseInt(txt_id.getText());
				} catch (NumberFormatException ex) {
					error = true;
					MessageBox box = new MessageBox(getShell(), 0);
					box.setText("Error");
					box.setMessage("There was an error. Please try again");
					box.open();
				}
				if (!error) {
					try {
						persCtr.removeInstructor(instructorId);
					} catch (Exception ex) {
						MessageBox box = new MessageBox(getShell(), 0);
						box.setText("Error");
						box.setMessage("There was an error. Please try again");
						box.open();
					}
					showAllInstructors();
					resetFields();
					btn_delete.setEnabled(false);
					btn_edit.setEnabled(false);
					btn_save.setEnabled(false);
					btn_create.setEnabled(true);
				}
			}
		});
		btn_delete.setLayoutData(new RowData(75, 50));
		btn_delete.setText("DELETE");
		btn_delete.setEnabled(false);

		btn_save.setLayoutData(new RowData(75, 50));
		btn_save.setText("SAVE");
		btn_save.setEnabled(false);

		btn_edit.setLayoutData(new RowData(75, 50));
		btn_edit.setEnabled(false);
		btn_edit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_delete.setEnabled(false);
				btn_edit.setEnabled(false);
				btn_save.setEnabled(true);
				btn_create.setEnabled(false);

				txt_id.setEditable(false);
				txt_firstName.setEditable(true);
				txt_surName.setEditable(true);
				txt_address.setEditable(true);
				txt_email.setEditable(true);
				txt_phoneNo.setEditable(true);
				txt_salary.setEditable(true);
				txt_skills.setEditable(true);
				search_name.setEditable(true);
			}
		});
		btn_edit.setText("EDIT");

		btn_create.setLayoutData(new RowData(75, 50));
		btn_create.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				btn_delete.setEnabled(false);
				btn_edit.setEnabled(false);
				btn_save.setEnabled(true);
				btn_create.setEnabled(false);

				resetFields();

				txt_id.setEditable(false);
				txt_firstName.setEditable(true);
				txt_surName.setEditable(true);
				txt_address.setEditable(true);
				txt_email.setEditable(true);
				txt_phoneNo.setEditable(true);
				txt_salary.setEditable(true);
				txt_skills.setEditable(true);
				search_name.setEditable(true);
			}
		});
		btn_create.setText("CREATE");

		Composite composite_6 = new Composite(composite_3, SWT.NONE);
		composite_6.setLayoutData(BorderLayout.CENTER);
		composite_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 40));

		Composite composite_7 = new Composite(composite_6, SWT.NONE);
		composite_7.setLayout(new GridLayout(2, false));

		Label lblId = new Label(composite_7, SWT.NONE);
		lblId.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false,
				1, 1));
		lblId.setBounds(0, 0, 55, 15);
		lblId.setText("ID:");

		txt_id = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_id = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1,
				1);
		gd_txt_id.widthHint = 203;
		txt_id.setEditable(false);
		txt_id.setLayoutData(gd_txt_id);

		Label lblFirstName = new Label(composite_7, SWT.NONE);
		lblFirstName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblFirstName.setText("First Name:");

		txt_firstName = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_fname = new GridData(SWT.LEFT, SWT.CENTER, true, false,
				1, 1);
		gd_txt_fname.widthHint = 203;
		txt_firstName.setEditable(false);
		txt_firstName.setLayoutData(gd_txt_fname);

		Label lblSurName = new Label(composite_7, SWT.NONE);
		lblSurName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblSurName.setText("Last Name:");

		txt_surName = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_sname = new GridData(SWT.LEFT, SWT.CENTER, true, false,
				1, 1);
		gd_txt_sname.widthHint = 203;
		txt_surName.setEditable(false);
		txt_surName.setLayoutData(gd_txt_sname);

		Label lblAddress = new Label(composite_7, SWT.NONE);
		lblAddress.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblAddress.setText("Address:");

		txt_address = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_address = new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1);
		gd_txt_address.widthHint = 203;
		txt_address.setEditable(false);
		txt_address.setLayoutData(gd_txt_address);

		Label lblEmail = new Label(composite_7, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblEmail.setText("Email:");

		txt_email = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_email = new GridData(SWT.LEFT, SWT.CENTER, true, false,
				1, 1);
		gd_txt_email.widthHint = 203;
		txt_email.setEditable(false);
		txt_email.setLayoutData(gd_txt_email);

		Label lblPhoneNo = new Label(composite_7, SWT.NONE);
		lblPhoneNo.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblPhoneNo.setText("Phone No:");

		txt_phoneNo = new Text(composite_7, SWT.BORDER);
		GridData gd_txt_phoneNo = new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1);
		gd_txt_phoneNo.widthHint = 203;
		txt_phoneNo.setEditable(false);
		txt_phoneNo.setLayoutData(gd_txt_phoneNo);

		Label lblSalary = new Label(composite_7, SWT.NONE);
		lblSalary.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblSalary.setText("Salary:");

		txt_salary = new Text(composite_7, SWT.BORDER);
		txt_salary.setEditable(false);
		txt_salary.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		Label lblSkills = new Label(composite_7, SWT.NONE);
		lblSkills.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		lblSkills.setText("Skills:");

		txt_skills = new Text(composite_7, SWT.BORDER);
		txt_skills.setEditable(false);
		txt_skills.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));

		table.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				int id = Integer.parseInt(table.getItem(
						table.getSelectionIndex()).getText(0));
				showInstructor(id);
			}
		});
		showAllInstructors();
	}

	private void showAllInstructors() {
		table.clearAll();
		table.setItemCount(0);
		LinkedList<Instructor> instructors = persCtr.getAllInstructors();
		for (Instructor instructor : instructors) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(instructor.getPersonId()));
			item.setText(1, instructor.getFirstName());
			item.setText(2, instructor.getSurName());
		}

	}

	private void showSearchedInstructors(String name) {
		table.clearAll();
		table.setItemCount(0);
		LinkedList<Instructor> instructors = persCtr
				.searchInstructorByName(name);
		for (Instructor instructor : instructors) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText(0, String.valueOf(instructor.getPersonId()));
			item.setText(1, instructor.getFirstName());
			item.setText(2, instructor.getSurName());
		}

	}

	private void showInstructor(int id) {
		Instructor instructor = persCtr.searchInstructorById(id);
		txt_id.setText(String.valueOf(instructor.getPersonId()));
		txt_firstName.setText(instructor.getFirstName());
		txt_surName.setText(instructor.getSurName());
		txt_address.setText(instructor.getAddress());
		txt_email.setText(instructor.getEmail());
		txt_phoneNo.setText(instructor.getPhoneNo());
		txt_salary.setText(String.valueOf(instructor.getSalary()));
		txt_skills.setText(instructor.getSkills());

		txt_id.setEditable(false);
		txt_firstName.setEditable(false);
		txt_surName.setEditable(false);
		txt_address.setEditable(false);
		txt_email.setEditable(false);
		txt_phoneNo.setEditable(false);
		txt_salary.setEditable(false);
		txt_skills.setEditable(false);

		btn_create.setEnabled(true);
		btn_edit.setEnabled(true);
		btn_delete.setEnabled(true);
		btn_save.setEnabled(false);
	}

	public void resetFields() {
		txt_id.setText("");
		txt_firstName.setText("");
		txt_surName.setText("");
		txt_address.setText("");
		txt_email.setText("");
		txt_phoneNo.setText("");
		txt_salary.setText("");
		txt_skills.setText("");
		search_name.setText("");
	}
}