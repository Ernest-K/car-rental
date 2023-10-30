import { Button } from "@radix-ui/themes";

function ButtonCheckbox({ name, checked, handleClick }) {
  return (
    <Button
      variant={`${checked ? "outline" : "solid"}`}
      onClick={handleClick}
      className="capitalize"
    >
      {name}
    </Button>
  );
}

export default ButtonCheckbox;
