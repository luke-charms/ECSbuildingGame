package students;

import building.Building;

public interface Student {

  int getLevel();

  int upgradeCost();

  int defence(Building building);
}