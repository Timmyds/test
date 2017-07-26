package com.fxb.world.service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
@SuppressWarnings("all")
public class RedisService {

	@Autowired
	private RedisTemplate redisTemplate;

	


	private static String redisCode = "utf-8";



	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public long del(final String... keys) {

		return (Long) redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long result = 0;
				for (int i = 0; i < keys.length; i++) {
					result = connection.del(keys[i].getBytes());
				}
				return result;
			}
		});
	}

	/**
	 * 通过key删除
	 * 
	 * @param key
	 */
	public void delete(final String... keys) {
		redisTemplate.delete(keys);
	}

	

	/**
	 * 添加key value 并且设置存活时间(byte)
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(final byte[] key, final byte[] value, final long liveTime) {
		redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key, value);
				if (liveTime > 0) {
					connection.expire(key, liveTime);
				}
				return 1L;
			}
		});
	}

	public Boolean setNX(final byte[] key, final byte[] value, final long liveTime) {
		return (Boolean) redisTemplate.execute(new RedisCallback() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				Boolean flag = connection.setNX(key, value);
				if (flag && liveTime > 0) {
					connection.expire(key, liveTime);
				}
				return flag;
			}
		});
	}

	/**
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public Boolean setNX(String key, String value, long liveTime) {
		return this.setNX(key.getBytes(), value.getBytes(), liveTime);
	}

	/**
	 * 添加key value 并且设置存活时间
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 *            单位秒
	 */
	public void set(String key, String value, long liveTime) {
		this.set(key.getBytes(), value.getBytes(), liveTime);
	}

	/**
	 * set 新增
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 *            有效时间 单位（秒）
	 */
	public void setObject(final String key, final byte[] value, final long liveTime) {
		this.set(key.getBytes(), value, liveTime);
	}

	/**
	 * 获取redis value (String)
	 * 
	 * @param key
	 * @return
	 */
	public byte[] get(final String key) {
		return (byte[]) redisTemplate.execute(new RedisCallback() {
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
	}

	/**
	 * 获取redis value (String)
	 * 
	 * @param key
	 * @return
	 */
	public String getString(final String key) {
		byte[] bytes = this.get(key);
		if (bytes != null) {
			return new String(this.get(key));
		} else {
			return null;
		}
	}

	/**
	 * 根据Key
	 * 
	 * @param key
	 * @param liveTime
	 *            有效时间 单位（秒）
	 */
	public void expire(final String key, final long liveTime) {

		redisTemplate.execute(new RedisCallback() {

			public Long doInRedis(RedisConnection connection) throws DataAccessException {

				if (liveTime > 0) {
					connection.expire(key.getBytes(), liveTime);
				}
				return 1L;

			}

		});

	}

	/**
	 * 通过正则匹配keys
	 * 
	 * @param pattern
	 * @return
	 */
	public Set keys(String pattern) {
		return redisTemplate.keys(pattern);

	}

	/**
	 * 压栈
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long push(String key, String value) {
		return redisTemplate.opsForList().leftPush(key, value);
	}

	/**
	 * 出栈
	 * 
	 * @param key
	 * @return
	 */
	public String pop(String key) {
		return (String) redisTemplate.opsForList().leftPop(key);
	}

	/**
	 * 入队
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public Long in(String key, String value) {
		return redisTemplate.opsForList().rightPush(key, value);
	}

	/**
	 * 出队
	 * 
	 * @param key
	 * @return
	 */
	public String out(String key) {
		return (String) redisTemplate.opsForList().rightPop(key);
	}

	/**
	 * 栈/队列长
	 * 
	 * @param key
	 * @return
	 */
	public Long length(String key) {
		return redisTemplate.opsForList().size(key);
	}

	/**
	 * 范围检索
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> range(String key, int start, int end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 移除
	 * 
	 * @param key
	 * @param i
	 * @param value
	 */
	public void remove(String key, long i, String value) {
		redisTemplate.opsForList().remove(key, i, value);
	}

	/**
	 * 检索
	 * 
	 * @param key
	 * @param index
	 * @return
	 */
	public String index(String key, long index) {
		return (String) redisTemplate.opsForList().index(key, index);
	}

	/**
	 * 置值
	 * 
	 * @param key
	 * @param index
	 * @param value
	 */
	public void set(String key, long index, String value) {
		redisTemplate.opsForList().set(key, index, value);
	}

	/**
	 * 裁剪
	 * 
	 * @param key
	 * @param start
	 * @param end
	 */
	public void trim(String key, long start, int end) {
		redisTemplate.opsForList().trim(key, start, end);
	}

	/**
	 * 使用set时，检查key是否已经存在
	 * 
	 * @param key
	 * @return true 存在，false 不存在
	 */
	public boolean exists(final String key) {
		return (Boolean) redisTemplate.execute(new RedisCallback() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] bkey = redisTemplate.getStringSerializer().serialize(key);
				return connection.exists(bkey);
			}
		});
	}

	/**
	 * 使用push,in时，检查key是否已经存在
	 * @param key
	 * @return
	 */
	public boolean hasKey(final String key) {
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 清空redis 所有数据
	 * 
	 * @return ok 清空成功
	 */
	public String flushDB() {
		return (String) redisTemplate.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.flushDb();
				return "ok";
			}
		});
	}

	/**
	 * 查看redis里有多少数据 return size
	 */
	public long dbSize() {
		return (Long) redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.dbSize();
			}
		});
	}

	/**
	 * 检查是否连接成功
	 * 
	 * @return PONG
	 */
	public String ping() {
		return (String) redisTemplate.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {

				return connection.ping();
			}
		});
	}

	public void close() {
		redisTemplate.execute(new RedisCallback() {
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				connection.close();
				return null;
			}
		});
	}
	
	/**
	 * 写入缓存
	 * 
	 * @param key
	 * @param value
	 * @return
	 *//*
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	*//**
	 * 写入缓存设置时效时间
	 * 
	 * @param key
	 * @param value
	 * @return
	 *//*
	public boolean set(final String key, Object value, long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	*//**
	 * 批量删除对应的value
	 * 
	 * @param keys
	 *//*
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	*//**
	 * 批量删除key
	 * 
	 * @param pattern
	 *//*
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	*//**
	 * 删除对应的value
	 * 
	 * @param key
	 *//*
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	*//**
	 * 通过key删除
	 * 
	 * @param key
	 *//*
	public long del(final String... keys) {

		return (Long) redisTemplate.execute(new RedisCallback() {
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				long result = 0;
				for (int i = 0; i < keys.length; i++) {
					result = connection.del(keys[i].getBytes());
				}
				return result;
			}
		});
	}

	*//**
	 * 判断缓存中是否有对应的value
	 * 
	 * @param key
	 * @return
	 *//*
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	*//**
	 * 读取缓存
	 * 
	 * @param key
	 * @return
	 *//*
	public String getString(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
		return result.toString();
	}

	*//**
	 * 获取redis value (String)
	 * 
	 * @param key
	 * @return
	 *//*
	public byte[] get(final String key) {
		return (byte[]) redisTemplate.execute(new RedisCallback() {
			public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.get(key.getBytes());
			}
		});
	}

	*//**
	 * 哈希 添加
	 * 
	 * @param key
	 * @param hashKey
	 * @param value
	 *//*
	public void hmSet(String key, Object hashKey, Object value) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		hash.put(key, hashKey, value);
	}

	*//**
	 * 哈希获取数据
	 * 
	 * @param key
	 * @param hashKey
	 * @return
	 *//*
	public Object hmGet(String key, Object hashKey) {
		HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
		return hash.get(key, hashKey);
	}

	*//**
	 * 列表添加
	 * 
	 * @param k
	 * @param v
	 *//*
	public void lPush(String k, Object v) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		list.rightPush(k, v);
	}

	*//**
	 * 列表获取
	 * 
	 * @param k
	 * @param l
	 * @param l1
	 * @return
	 *//*
	public List<Object> lRange(String k, long l, long l1) {
		ListOperations<String, Object> list = redisTemplate.opsForList();
		return list.range(k, l, l1);
	}

	*//**
	 * 集合添加
	 * 
	 * @param key
	 * @param value
	 *//*
	public void add(String key, Object value) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		set.add(key, value);
	}

	*//**
	 * 集合获取
	 * 
	 * @param key
	 * @return
	 *//*
	public Set<Object> setMembers(String key) {
		SetOperations<String, Object> set = redisTemplate.opsForSet();
		return set.members(key);
	}

	*//**
	 * 有序集合添加
	 * 
	 * @param key
	 * @param value
	 * @param scoure
	 *//*
	public void zAdd(String key, Object value, double scoure) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		zset.add(key, value, scoure);
	}

	*//**
	 * 有序集合获取
	 * 
	 * @param key
	 * @param scoure
	 * @param scoure1
	 * @return
	 *//*
	public Set<Object> rangeByScore(String key, double scoure, double scoure1) {
		ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
		return zset.rangeByScore(key, scoure, scoure1);
	}

	*//**
	 * 添加key value 并且设置存活时间(byte)
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 *//*
	public void set(final byte[] key, final byte[] value, final long liveTime) {
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, liveTime, TimeUnit.SECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	*//**
	 * set 新增
	 * 
	 * @param key
	 * @param value
	 * @param liveTime
	 *            有效时间 单位（秒）
	 *//*
	public void setObject(final String key, final Object value, final long liveTime) {
		this.set(key, value, liveTime);
	}

	*//**
	 * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET
	 * if Not eXists』(如果不存在，则 SET)的简写。 <br>
	 * 保存成功，返回 true <br>
	 * 保存失败，返回 false
	 *//*
	public Boolean setNX(final byte[] key, final byte[] value, final long liveTime) {
		return (Boolean) redisTemplate.execute(new RedisCallback() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				Boolean flag = connection.setNX(key, value);
				if (flag && liveTime > 0) {
					connection.expire(key, liveTime);
				}
				return flag;
			}
		});
	}

	*//**
	 * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET
	 * if Not eXists』(如果不存在，则 SET)的简写。 <br>
	 * 保存成功，返回 true <br>
	 * 保存失败，返回 false
	 * 
	 * @param key
	 * @param val
	 * @param expire
	 *            超时时间
	 * @return 保存成功，返回 true 否则返回 false
	 *//*
	public Boolean setNX(String key, String value, long liveTime) {
		return this.setNX(key.getBytes(), value.getBytes(), liveTime);
	}
	 *//** 
     * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET 
     * if Not eXists』(如果不存在，则 SET)的简写。 <br> 
     * 保存成功，返回 true <br> 
     * 保存失败，返回 false 
     *//*  
    public  boolean saveNX(String key, String val) {  
  
        *//** 设置成功，返回 1 设置失败，返回 0 **//*  
        return (boolean) redisTemplate.execute((RedisCallback<Boolean>) connection -> {  
            return connection.setNX(key.getBytes(), val.getBytes());  
        });  
  
    }  
  
    *//** 
     * 将 key的值保存为 value ，当且仅当 key 不存在。 若给定的 key 已经存在，则 SETNX 不做任何动作。 SETNX 是『SET 
     * if Not eXists』(如果不存在，则 SET)的简写。 <br> 
     * 保存成功，返回 true <br> 
     * 保存失败，返回 false 
     * 
     * @param key 
     * @param val 
     * @param expire 
     *            超时时间 
     * @return 保存成功，返回 true 否则返回 false 
     *//*  
    public  boolean saveNX(String key, String val, int expire) {  
  
        boolean ret = saveNX(key, val);  
        if (ret) {  
        	redisTemplate.expire(key, expire, TimeUnit.SECONDS);  
        }  
        return ret;  
    }  
	*//**
	 * 根据Key
	 * 
	 * @param key
	 * @param liveTime
	 *            有效时间 单位（秒）
	 *//*
	public void expire(final String key, final long liveTime) {

		redisTemplate.execute(new RedisCallback() {

			public Long doInRedis(RedisConnection connection) throws DataAccessException {

				if (liveTime > 0) {
					connection.expire(key.getBytes(), liveTime);
				}
				return 1L;

			}

		});

	}
	
	  *//** 
     * 取得复杂类型数据 
     * 
     * @param key 
     * @param obj 
     * @param clazz 
     * @return 
     *//*  
    public  <T> T getBean(String key, Class<T> clazz) {  
  
        String value = (String) redisTemplate.opsForValue().get(key);  
        if (value == null) {  
            return null;  
        }  
        return JSON.parseObject(value, clazz);  
    } 
    *//** 
     * 保存复杂类型数据到缓存 
     * 
     * @param key 
     * @param obj 
     * @return 
     *//*  
    public  void saveBean(String key, Object obj) {  
  
        redisTemplate.opsForValue().set(key, JSON.toJSONString(obj));  
    }  
  
    *//** 
     * 保存复杂类型数据到缓存（并设置失效时间） 
     * 
     * @param key 
     * @param Object 
     * @param seconds 
     * @return 
     *//*  
    public  void saveBean(String key, Object obj, int seconds) {  
  
        redisTemplate.opsForValue().set(key, JSON.toJSONString(obj), seconds, TimeUnit.SECONDS);  
    }  */
}
