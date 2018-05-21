package tk.mybatis.mapper.mapperhelper.id;

import tk.mybatis.mapper.mapperhelper.id.sequence.SequenceUtils;
import tk.mybatis.mapper.util.StringUtil;

/**
 * 主键生成策略
 * @date: 2018年1月9日 上午9:49:18
 * @author: jiuzhou.hu
 */
public class IdGenerator {
    
    /**
     * 基于Twitter的Snowflake算法实现分布式高效有序ID
     * @author jiuzhou.hu
     * @date 2018年1月9日 上午9:49:30
     * @return
     */
    public static String sequenceId() {
        return "SELECT ${@"+SequenceUtils.class.getName()+"@genId()}";
    }
    
    /**
     * 生成32位无“-”的UUID
     * @author jiuzhou.hu
     * @date 2018年1月9日 上午9:52:43
     * @return
     */
    public static String uuid() {
        return "SELECT ${@"+StringUtil.class.getName()+"@createSystemDataPrimaryKey()}";
    }
}
