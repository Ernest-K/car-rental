import { Price } from "@/types/interfaces";
import { ReservationInput } from "@/types/types";

export const calcTotalPrice = (
  price: Price,
  startDate: Date,
  endDate: Date
) => {
  let numberOfDays: number;
  if (startDate.getTime() == endDate.getTime()) {
    numberOfDays = 1;
  } else {
    const differenceInTime = endDate.getTime() - startDate.getTime();
    numberOfDays = Math.ceil(differenceInTime / (1000 * 60 * 60 * 24)) + 1;
  }

  switch (numberOfDays) {
    case 1:
      return price.forDay;
    case 2:
    case 3:
    case 4:
      return price.forTwoToFourDays * numberOfDays;
    default:
      return price.forWeek * numberOfDays;
  }
};

export const formatDate = (date: Date) => {
  const offset = date.getTimezoneOffset();
  const result = new Date(date.getTime() - offset * 60 * 1000);

  return result.toISOString().split("T")[0];
};

export const prepareReservationRequest = (data: ReservationInput) => {
  return JSON.stringify({
    ...data,
    startDate: formatDate(data.startDate),
    endDate: formatDate(data.endDate),
  });
};
