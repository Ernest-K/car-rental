export type statusType = "AVAILABLE" | "UNAVAILABLE";
export type fuelType = "PETROL" | "DIESEL" | "HYBRID" | "ELECTRIC";
export type transmissionType = "MANUAL" | "AUTOMATIC";
export type driveType = "REAR" | "FRONT" | "ALL_WHEEL";

export type ReservationInput = {
  startDate: Date;
  endDate: Date;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
};

export type EditReservationInput = {
  startDate: Date;
  endDate: Date;
  firstName: string;
  lastName: string;
  email: string;
  phoneNumber: string;
  cost: number;
};

export type EditCarInput = {
  status: statusType;
  make: string;
  model: string;
  productionYear: number;
  power: number;
  fuelType: fuelType;
  transmissionType: transmissionType;
  driveType: driveType;
  priceForDay: number;
  priceForTwoToFourDays: number;
  priceForWeek: number;
  categoryId: number;
};
