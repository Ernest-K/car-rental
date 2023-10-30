import { driveType, fuelType, statusType, transmissionType } from "./types";

export interface CarInfo {
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

export interface CarDetailInfo {
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

// type statusType = "AVAILABLE" | "UNAVAILABLE";
// type fuelType = "PETROL" | "DIESEL" | "HYBRID" | "ELECTRIC";
// type transmissionType = "MANUAL" | "AUTOMATIC";
// type driveType = "REAR" | "FRONT" | "ALL_WHEEL";
