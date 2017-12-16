package istu.bacs.externalapi.sybon;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.Data;

@Data
class SybonProblem {
    private Integer id;
    private String name;
    private String statementUrl;
    private Integer collectionId;
    private Integer testsCount;
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private Object[] pretest;
    private SybonResourceLimits resourceLimits;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    public void setPretests(Object[] pretests) {
        this.pretest = pretests;
    }
}