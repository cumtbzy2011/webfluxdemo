/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.xinan.demo.rest;

import io.reactivex.Observable;
import io.reactivex.Single;
import org.davidmoten.rx.jdbc.Database;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;


/**
 * spring-data-jpa是同步的，repository返回的结果并不是Mono或者Flux形式。
 *      可以使用第三方异步jdbc连接池rxjava2-jdbc，但是由于每个方法是异步的，
 * 当数个异步方法组合起来时，并不能保证每个方法都是由一个线程按顺序调用的，
 * 这就使基于ThreadLocal的@Transactional无法使用
 *      当然，可以手动在一个异步方法中开启并提交事务，但是这还是失去了@Transactional组合
 * 不同方法到一个事物的便利性和可扩展性
 * @author xinan
 */
@Component
public class RxJava2PostRepository {
    private Database db;

    RxJava2PostRepository(Database db) {
        this.db = db;
    }

    public Observable<Post> findAll() {
        return this.db.select("select * from posts")
            .get(
                rs -> new Post(rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("content")
                )
            )
            .toObservable();
    }

    public Single<Post> findById(Long id) {
        return this.db.select("select * from posts where id=?")
            .parameter(id)
            .get(
                rs -> new Post(rs.getLong("id"),
                    rs.getString("title"),
                    rs.getString("content")
                )
            )
            .firstElement()
            .toSingle();
    }

    public Single<Integer> save(Post post) {
        return this.db.update("insert into posts(title, content) values(?, ?)")
            .parameters(post.getTitle(), post.getContent())
            .returnGeneratedKeys()
            .getAs(Integer.class)
            .firstElement()
            .toSingle();
    }

    String sql = "insert into posts(title, content) values(?, ?)";

    public Single<Integer> saveTx(Post post) {
        return db.connection()
          .map(connection -> {
              connection.setAutoCommit(false);
              PreparedStatement pstmt = connection.prepareStatement(sql);
              pstmt.setString(1, post.getContent());
              pstmt.setString(2, post.getContent());
              int i = pstmt.executeUpdate();
              pstmt.close();
              connection.commit();
              return i;
          });
    }


}
