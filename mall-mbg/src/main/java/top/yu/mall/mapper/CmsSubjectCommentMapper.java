package top.yu.mall.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import top.yu.mall.domain.CmsSubjectComment;
import top.yu.mall.domain.CmsSubjectCommentExample;

public interface CmsSubjectCommentMapper {
    long countByExample(CmsSubjectCommentExample example);

    int deleteByExample(CmsSubjectCommentExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CmsSubjectComment record);

    int insertSelective(CmsSubjectComment record);

    List<CmsSubjectComment> selectByExample(CmsSubjectCommentExample example);

    CmsSubjectComment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CmsSubjectComment record, @Param("example") CmsSubjectCommentExample example);

    int updateByExample(@Param("record") CmsSubjectComment record, @Param("example") CmsSubjectCommentExample example);

    int updateByPrimaryKeySelective(CmsSubjectComment record);

    int updateByPrimaryKey(CmsSubjectComment record);
}