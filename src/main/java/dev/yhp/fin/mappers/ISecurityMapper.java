package dev.yhp.fin.mappers;

import dev.yhp.fin.interfaces.IResult;
import dev.yhp.fin.models.ClientModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
@SuppressWarnings("UnusedReturnValue")
public interface ISecurityMapper {
    int insertBlockedIp(@Param("clientModel") ClientModel clientModel,
                        @Param("minutesToBlock") int minutesToBlock);

    int insertIllegalLog(@Param("clientModel") ClientModel clientModel,
                         @Param("result") IResult<? extends Enum<?>> result);

    int selectIllegalLogCount(@Param("clientModel") ClientModel clientModel,
                              @Param("lookBackSeconds") int lookBackSeconds);

    int selectBlockedIpCount(ClientModel clientModel);

    int selectBlockedIpCountSoFar(ClientModel clientModel);
}