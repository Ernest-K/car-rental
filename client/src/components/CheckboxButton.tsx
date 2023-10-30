import { Button } from "@radix-ui/themes";
import { MouseEventHandler } from "react";

interface CheckboxButtonProps {
  name: string;
  checked: boolean;
  handleClick: MouseEventHandler<HTMLButtonElement>;
}

function CheckboxButton({ name, checked, handleClick }: CheckboxButtonProps) {
  return (
    <Button
      size={"1"}
      variant={`${checked ? "outline" : "solid"}`}
      onClick={handleClick}
      className="capitalize"
    >
      {name}
    </Button>
  );
}

export default CheckboxButton;
