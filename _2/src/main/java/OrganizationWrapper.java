import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
row1: id=1, parentId=null, name="RTK"
row2: id=2, parentId=1, name="Sales"
row3: id=3, parentId=2, name="Marketing"

[{
  "id": 1,
  "name": "Rostelecom",
  "childs": [
    {
      "id": 2,
      "name": "Sales",
      "childs": [
        {
          "id": 3,
          "name": "Marketing",
          "childs": [
          ]
        }
      ]
    }
  ]
}]
*/
class OrganizationEntity {
    public Long id;
    public Long parentId;
    public String name;
}

class OrganizationDto {
    public Long id;
    public String name;
    public List<OrganizationDto> childs;

    public OrganizationDto(Long id, String name, List<OrganizationDto> childs) {
        this.id = id;
        this.name = name;
        this.childs = childs;
    }
}

public class OrganizationWrapper {

    public List<OrganizationDto> mapper(List<OrganizationEntity> entities) {
        Map<Long, OrganizationDto> roots = new HashMap<>();
        Map<Long, OrganizationDto> childs = new HashMap<>();

        for (OrganizationEntity entity : entities) {
            if (entity.parentId == null) {
                roots.put(entity.id, new OrganizationDto(entity.id, entity.name, new ArrayList<>()));
            } else {
                var organizationDto = childs.get(entity.parentId);
                if (organizationDto == null) {
                    childs.put(entity.id, new OrganizationDto(entity.id, entity.name, new ArrayList<>()));
                } else {
                    organizationDto.childs.add(new OrganizationDto(entity.id, entity.name, new ArrayList<>()));
                }
            }
        }

        //return roots;
        return null;
    }
}
