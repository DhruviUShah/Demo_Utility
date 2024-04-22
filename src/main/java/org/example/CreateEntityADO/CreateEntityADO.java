package org.example.CreateEntityADO;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class CreateEntityADO {
    private List<Map<String, Object>> fields;

    public CreateEntityADO() {
        this.fields = new ArrayList<>();
        addField("/fields/System.Title", "Test entity ADO");
        addField("/fields/System.Description", "Test description from ADO");
    }

    public void addField(String path, Object value) {
        Map<String, Object> field = new HashMap<>();
        field.put("op", "add");
        field.put("path", path);
        field.put("from", null);
        field.put("value", value);
        fields.add(field);
    }
}