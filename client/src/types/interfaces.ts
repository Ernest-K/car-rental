import { driveType, fuelType, statusType, transmissionType } from "./types";

export interface Car {
  id: number;
  status: statusType;
  make: string;
  model: string;
  price: Price;
  category: Category;
}

export interface Price {
  id: number;
  forDay: number;
  forTwoToFourDays: number;
  forWeek: number;
}

export interface Category {
  id: number;
  name: string;
}

export interface CarDetail {
  id: number;
  status: statusType;
  make: string;
  model: string;
  productionYear: number;
  power: number;
  fuelType: fuelType;
  transmissionType: transmissionType;
  driveType: driveType;
  price: Price;
  category: Category;
}

export interface Driver {
  id: number;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
}

export interface Reservation {
  id: number;
  startDate: Date;
  endDate: Date;
  car: Car;
  driver: Driver;
  cost: number;
}
