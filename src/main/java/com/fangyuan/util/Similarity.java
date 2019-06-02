package com.fangyuan.util;


import com.fangyuan.dao.RoomDAO;
import com.fangyuan.vo.SimilarityVO;
import com.mysql.cj.conf.ConnectionUrlParser;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class Similarity {
    public static boolean isInit = false;
    @Autowired
    RoomDAO roomDAO;
    // key -> roomId， value -> 四个特征向量
    public static Map<Integer, List<Integer>> rooms = new ConcurrentHashMap<>();
    // district, price， area, layout,
    public static final double[] weight = new double[]{0.25, 0.25, 0.25, 0.25};

    public void init(){
        if(isInit){
            return;
        }
        List<SimilarityVO> vos = roomDAO.batchQueryAll4Similarity();
        System.out.println(vos.size());
        System.out.println(vos.get(0));
        rooms = vos.stream().collect(Collectors.toMap(SimilarityVO::getRoomId, vo ->{
            List<Integer> list = new ArrayList<>();
            list.add(vo.getDistrict());
            list.add(vo.getPrice());
            list.add(vo.getArea());
            list.add(vo.getLayout());
            return list;
        }));
        isInit = true;
    }
    /**
     * 两个向量的余弦值
     * @param vectorA
     * @param vectorB
     * @return
     */
    public static double cosineSimilarity(List<Integer> vectorA, List<Integer> vectorB, int exclude){
        int molA = 0;
        int molB = 0;
        int product = 0;
        for (int i = 0; i < vectorA.size(); i++){
            if (i == exclude){
                continue;
            }
            product += vectorA.get(i) * vectorB.get(i);
            molA += vectorA.get(i) * vectorA.get(i);
            molB += vectorB.get(i) * vectorB.get(i);
        }
        return product * 1.0 / Math.sqrt(molA * molB);
    }

    /**
     * 计算两个向量的相似度（加权）
     * @param vectorA
     * @param vectorB
     * @return
     */
    public static double cosineSimilarityWithWeight(List<Integer> vectorA, List<Integer> vectorB){
        double[] cos = new double[vectorA.size()];
        for (int i = 0; i < cos.length; i++){
            cos[i] = cosineSimilarity(vectorA, vectorB, i);
        }
        double sim = 0;
        for (int i = 0; i < weight.length; i++){
            sim += weight[i] * cos[i];
        }
        return sim;
    }

    public static void main(String[] args) {
        System.out.println(cosineSimilarity(Arrays.asList(1, 2, 3, 4), Arrays.asList(1, 0, 0, 0), -1));
    }

    /**
     * 传入用户特征，获得推荐
     * @param userChar
     * @param num
     * @return
     */
    public List<Integer> getRecommend(List<Integer> userChar, int num){
        List<Integer> ids = new ArrayList<>();
        List<Pair<Double, Integer>> sims = new ArrayList<>(rooms.size());
        // 计算相似度
        for (Map.Entry<Integer, List<Integer>> entry : rooms.entrySet()){
//            double sim = cosineSimilarityWithWeight(userChar, entry.getValue());
            double sim = cosineSimilarity(userChar, entry.getValue(), -1);
            sims.add(new MutablePair(sim, entry.getKey()));
        }
        // 遍历取前num个
        for (int i = 0; i < num; i++){
            int maxPos = 0;
            double maxVal = Double.MIN_VALUE;
            for (int j = 0; j < sims.size(); j++){
                if(sims.get(j).getKey() > maxVal){
                    maxPos = j;
                    maxVal = sims.get(j).getKey();
                }
            }
            // 相似度 --> id
            Pair<Double, Integer> maxSim = sims.get(maxPos);
            ids.add(maxSim.getValue());
            maxSim = new MutablePair<>(Double.MIN_VALUE, maxSim.getValue());
            sims.set(maxPos, maxSim);
        }
        return ids;
    }
}
