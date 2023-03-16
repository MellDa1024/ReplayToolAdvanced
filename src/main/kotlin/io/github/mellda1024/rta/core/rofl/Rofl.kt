package io.github.mellda1024.rta.core.rofl

import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

data class Rofl(
    val file: File,
    val name: String,
    val path: String
) {
    constructor(file: File) : this(file, file.name, file.path)

    /**
     * Rofl 파일의 ByteArray값을 가지고 와서 Version을 인식합니다.
     * Rofl의 Json 메타데이터가 Hex Offset 기준 120(Decimal 288)에서 시작하므로,
     * 처음 RIOT 문자열 확인으로 포인터를 4만큼 움직이니 284만큼 더 skip합니다.
     * 라이엇에서 Rofl 구조를 바꾸지 않았으면 좋겠네요 ㅎㅎ;
     */

    val version: String
        get() {
            try {
                val temp = ByteArray(4)
                val riotCheck = byteArrayOf(82, 73, 79, 84)
                val fis = FileInputStream(file)
                fis.read(temp)
                if (!riotCheck.contentEquals(temp)) {
                    fis.close()
                    return "null"
                }

                val versionByteArray = ByteArray(64)
                fis.skip(284)
                fis.read(versionByteArray)
                val versionRaw = String(versionByteArray, StandardCharsets.UTF_8)
                val regex = Pattern.compile("\"gameVersion\":\"([0-9.]*)\",")
                val match = regex.matcher(versionRaw)
                if (match.find()) {
                    fis.close()
                    return match.group(1)
                }
                fis.close()
                return "null"
            } catch(e : IOException) {
                return "null"
            }
        }

    val createdDate: String
        get() {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return try {
                val time = Files.readAttributes(this.file.toPath(), BasicFileAttributes::class.java)
                    .creationTime()
                sdf.format(Date(time.toMillis()))
            } catch (e: IOException) {
                "null"
            }
        }

    fun execute(file: File) {
        val driveLetter = Paths.get(file.path).root.toString().dropLast(1)
        val folder = file.parent

        val command =
            "cmd /c start cmd.exe /K " +
            "\"cd /D $driveLetter &&" +
            "cd $folder &&" +
            "\"League of Legends.exe\" \"${this.path}\" -GameBaseDir=$folder -Locale=ko_KR -SkipBuild -EnableLNP -UseNewX3D=1 -UseNewX3DFramebuffers=1 &&" +
            "exit\""


        Runtime.getRuntime().exec(command)
    }

    fun saveBatchFile(file: File, saveDir: File): Boolean {
        val driveLetter = Paths.get(file.path).root.toString().dropLast(1)
        val folder = file.parent
        val replayBatFile = File(saveDir.path + "/replay.bat")

        if (!replayBatFile.exists()) replayBatFile.createNewFile()
        return try {
            val fw = FileWriter("replay.bat")
            fw.write("cd /D $driveLetter\n")
            fw.write("cd $folder\n")
            fw.write("\"League of Legends.exe\" \"${this.path}\" -GameBaseDir=$folder -Locale=ko_KR -SkipBuild -EnableLNP -UseNewX3D=1 -UseNewX3DFramebuffers=1")
            fw.close()
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}

