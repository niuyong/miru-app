import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:miru_app/utils/i18n.dart';
import 'package:miru_app/views/pages/settings/settings_page.dart';
import 'package:miru_app/views/widgets/platform_widget.dart';
import 'package:miru_app/views/pages/extension/extension_repo_page.dart';
import 'package:miru_app/views/pages/extension/extension_page.dart';

class SettingsPagePre extends StatefulWidget {
  const SettingsPagePre({super.key});

  @override
  State<SettingsPagePre> createState() => _SettingsPagePreState();
}

class _SettingsPagePreState extends State<SettingsPagePre> {
  @override
  void initState() {
    super.initState();
  }

  List<Widget> _buildContent() {
    return [
      if (!Platform.isAndroid) ...[
        Text(
          'common.settings'.i18n,
          style: const TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
        ),
        const SizedBox(height: 16),
      ],
      InkWell(
        //单击事件响应
        onTap: () {
          Get.to(
            // () => const ExtensionRepoPage(),
            () => const ExtensionPage(),
          );
        },
        child: const Row(children: [
          SizedBox(
            height: 60,
            width: 10,
          ),
          Icon(
            Icons.extension,
            size: 36.0,
          ),
          SizedBox(
            height: 60,
            width: 10,
          ),
          Text('我的扩展')
        ]),
      ),
      // Divider( // 添加分割线
      //   height: 1.0, // 可以根据需要调整高度
      //   thickness: 1.0, // 可以根据需要调整厚度
      //   indent: 20.0, // 左侧缩进
      //   endIndent: 20.0, // 右侧缩进
      //   color: Colors.black.withOpacity(0.5), // 可以根据需要调整颜色
      // ),
      InkWell(
        //单击事件响应
        onTap: () {
          Get.to(
            () => const SettingsPage(),
          );
        },
        child: const Row(children: [
          SizedBox(
            height: 60,
            width: 10,
          ),
          Icon(
            Icons.settings,
            size: 36.0,
          ),
          SizedBox(
            height: 60,
            width: 10,
          ),
          Text('设置')
        ]),
      )
    ];
  }

  Widget _buildAndroid(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('common.settings'.i18n),
      ),
      body: ListView(
        padding: const EdgeInsets.symmetric(vertical: 10),
        children: _buildContent(),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return PlatformBuildWidget(
      androidBuilder: _buildAndroid,
      desktopBuilder: (context) => ListView(
        padding: const EdgeInsets.symmetric(horizontal: 30, vertical: 20),
        children: _buildContent(),
      ),
    );
  }
}
