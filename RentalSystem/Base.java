
package RentalSystem;
import java.util.*;
class Car{
	private String CarId;
	private String Brand;
	private String Model;
	private double Basepriceperpay;
	private boolean isAvailable;
	public Car(String CarId,String Brand,String Model,double Basepriceperpay,boolean isAvailable) {
		this.CarId=CarId;
		this.Brand=Brand;
		this.Model=Model;
		this.Basepriceperpay=Basepriceperpay;
		this.isAvailable=true;
	}
	public String getCarId() {
		return CarId;
	}
	public String getBrand() {
		return Brand;
	}
	public String getModel() {
		return Model;
	}
	public double CalculatePrice(int rentalDays) {
		return Basepriceperpay*rentalDays;
	}
	public boolean isAvailable() {
		return isAvailable;
	}
	public void rent() {
		isAvailable=false;
	}
	public void ReturnCar() {
		isAvailable=true;
	}
}
class Customer{
	private String CustomerId;
	private String name;
public Customer(String CustomerId,String name) {
	this.CustomerId=CustomerId;
	this.name=name;
}
public String getCustomerId() {
	return CustomerId;
}
public String getname() {
	return name;
}
}
class Rental{
	private Car car;
	private Customer customer;
	private int days;
	public Rental(Car car,Customer customer,int days) {
		this.car=car;
		this.customer=customer;
		this.days=days;
	}
	public Car getcar() {
		return car;
	}
	public Customer getcustomer() {
		return customer;
	}
	public int getdays() {
		return days;
	}
}
class CarRentalSystem{
	private List<Car>cars;
	private List<Customer>customers;
	private List<Rental>rentals;
	public CarRentalSystem() {
		cars=new ArrayList<>();
		customers=new ArrayList<>();
		rentals=new ArrayList<>();
	}
	public void addCar(Car car) {
		cars.add(car);
	}
	public void addCustomer(Customer customer) {
		customers.add(customer);
	}
	public void rentCar(Car car, Customer customer ,int days) {
		if(car.isAvailable()) {
			car.rent();
			rentals.add(new Rental(car,customer,days));
		}
		else {
			System.out.println("Car is not available for rent");
		}
	}
	public void ReturnCar(Car car) {
		car.ReturnCar();
		Rental rentalToRemove=null;
		for(Rental rental:rentals) {
			if(rental.getcar()==car) {
				rentalToRemove=rental;
				break;
			}
		}
		if(rentalToRemove!=null) {
			rentals.remove(rentalToRemove);
			System.out.println("Cars returned successfully");
		}
		else {
			System.out.println("Cars was not rented");
		}
	}

public void menu() {
	Scanner s=new Scanner(System.in);
	while(true) {
		System.out.println("----Tandon Car Rental System------------");
		System.out.println("1:Rent a Car");
		System.out.println("2:Return a Car");
		System.out.println("3:Exit");
		System.out.println("Enter a choice");
		int choice=s.nextInt();
		s.nextLine();
		if(choice==1) {
			System.out.println("\n----------Rent a Car-----------\n");
			System.out.println("enter your name");
			String customerName=s.nextLine();
			System.out.println("\n Available Cars:");
			for(Car car:cars) {
				if(car.isAvailable()) {
					System.out.println(car.getCarId()+"-"+car.getBrand()+"-"+car.getModel());
				}
			}
			System.out.println("\nEnter the Car Id you want to rent:");
			String CarId=s.nextLine();
			System.out.println("\nEnter no of days for rental");
			int rentalDays=s.nextInt();
			s.nextLine();
			Customer newcustomer =new Customer("CUS"+(customers.size()+1),customerName);
			addCustomer(newcustomer);
			Car selectedCar=null;
			for(Car car:cars) {
				if(car.getCarId().equals(CarId)&&car.isAvailable()) {
					selectedCar=car;
					break;
				}
			}
			if(selectedCar!=null) {
				double TotalPrice=selectedCar.CalculatePrice(rentalDays);
				System.out.println("\n==Rental Information==-----------------");
				System.out.println("customerId"+newcustomer.getCustomerId());
				System.out.println("customer Name"+newcustomer.getname());
				System.out.println("Car:"+selectedCar.getBrand()+""+selectedCar.getModel());
				System.out.println("Rental Days:"+rentalDays);
				System.out.printf("Total Price:$%.2f%n", TotalPrice);
				System.out.printf("\nConfirm Rental(Y/N):");
			    String Confirm = s.nextLine();
				if(Confirm.equalsIgnoreCase("Y")) {
					rentCar(selectedCar,newcustomer,rentalDays);
					System.out.println("\n Car rented Successfully");
				}
		
				else {
					System.out.println("\nRental Cancel");
				}
			}
				else {
					System.out.println("\n Invalid  car selection or car not available for rent");
				}
		}
				
			else if(choice==2) {
				System.out.println("\n------------Return a Car:----------\n");
				System.out.println("\nEnter the CarId you want to return:");
				String carId=s.nextLine();
				Car carToreturn=null;
				for(Car car:cars) {
					if(car.getCarId().equals(carId)&&!car.isAvailable()) {
						carToreturn=car;
						break;
					}
				}
				if(carToreturn!=null) {
					Customer customer=null;
					for(Rental rental:rentals) {
						if(rental.getcar()==carToreturn) {
							customer=rental.getcustomer();
							break;
						}
					}
					if(customer!=null) {
						ReturnCar(carToreturn);
						System.out.println("\nCar returned Successfully by:"+customer.getname());
					}
					else {
						System.out.println("\n Car was not rented or information was missing");
					}
				}
				else {
					System.out.println("\nInvalid carid or car is not rented");
				}
			}
			else if(choice==3) {
				break;
			}else {
				System.out.println("\n --------Invalid choice.Please enter valid choice-------");
			}
		
	}
	System.out.println("\nThank you for using the Tandon's Car Rental system");
}
}

public class Base {
	public static void main(String[]args) {
		CarRentalSystem crs=new CarRentalSystem(); 
		Car car1=new Car("C001","Toyoto","camry",60.0, false);
		Car car2=new Car("C002","Honda","Accord",70.0, false);
		Car car3=new Car("C003","Tech Mahindra","Thar",150.0, false);
		crs.addCar(car3);
		crs.addCar(car2);
		crs.addCar(car1);
		crs.menu();
	}
}

